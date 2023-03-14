using System;
using System.Collections;
using System.Collections.Generic;
using System.Diagnostics;
using System.IO;
using System.Linq;
using System.Net;
using System.Net.NetworkInformation;
using System.Security.Cryptography;
using System.Text;
using System.Threading.Tasks;

namespace Scripts
{
    public class Web
    {
        private static readonly object LockObject = new object();
        private static readonly int FILE_DOWNLOAD_ATTEMPTS = 50;

        public static int DownloadFiles(params string[] args)
        {
            WriteLine("Network interfaces:");
            NetworkInterface[] interfaces = NetworkInterface.GetAllNetworkInterfaces();
            foreach (var networkInterface in interfaces)
            {
                if (networkInterface.OperationalStatus != OperationalStatus.Up)
                {
                    continue;
                }

                WriteLine("\t{0}\t\"{1}\"\t\"{2}\"", networkInterface.NetworkInterfaceType, networkInterface.Name, networkInterface.Description);
                IPInterfaceProperties ipProperties = networkInterface.GetIPProperties();
                IPAddressCollection dnsAddresses = ipProperties.DnsAddresses;

                foreach (IPAddress dnsAddress in dnsAddresses)
                {
                    WriteLine("\t\tDNS address {0}", dnsAddress);
                }
            }

            List<Task<bool>> tasks = new List<Task<bool>>();
            for (int i = 0; i < args.Length / 2; i++)
            {
                tasks.Add(DownloadFile(args[i * 2], args[i * 2 + 1], FILE_DOWNLOAD_ATTEMPTS, TimeSpan.FromSeconds(10)));
            }

            Task.WhenAll(tasks).Wait();
            return tasks.All(i => i.Result) ? 0: 1;
        }

        /// <summary>
        /// Downloads file with an afterward confirmation of its hash, based on hashing algorithm encapsulated within URL.
        /// </summary>
        /// <param name="sourceUrl">URL for the fle to be downloaded</param>
        /// <param name="destinationFile">destination path to which the file should be downloaded</param>
        /// <param name="attempts">amount of attempts to download the file</param>
        /// <param name="delay">delay between the attempts</param>
        /// <returns>asynchronous download task</returns>
        private static async Task<bool> DownloadFile(string sourceUrl, string destinationFile, int attempts, TimeSpan delay)
        {
            string name = Path.GetFileName(destinationFile);
            bool success = false;
            for (int i = 0; i < attempts; i++)
            {
                try
                {
                    string[] sourceParts = sourceUrl.Split('#');
                    HashAlgorithm hashAlgorithm = null;
                    string hashAlgorithmName = string.Empty;
                    string hashValue = string.Empty;
                    byte[] hash = null;
                    if (sourceParts.Length == 3) // has hash
                    {
                        sourceUrl = sourceParts[0];
                        hashAlgorithmName = sourceParts[1].ToUpperInvariant();
                        hashValue = sourceParts[2].ToLowerInvariant();
                        try
                        {
                            List<byte> hashBytes = new List<byte>();
                            for (int j = 0; j < hashValue.Length; j += 2)
                            {
                                hashBytes.Add(Convert.ToByte(hashValue.Substring(j, 2), 16));
                            }

                            hash = hashBytes.ToArray();
                        }
                        catch
                        {
                            throw new InvalidOperationException("Invalid hash: " + hashValue);
                        }

                        switch (hashAlgorithmName)
                        {
                            case "SHA384":
                                hashAlgorithm = SHA384.Create();
                                break;

                            case "SHA512":
                                hashAlgorithm = SHA512.Create();
                                break;

                            case "SHA256":
                                hashAlgorithm = SHA256.Create();
                                break;

                            case "SHA1":
                                hashAlgorithm = SHA1.Create();
                                break;

                            case "MD5":
                                hashAlgorithm = MD5.Create();
                                break;

                            default:
                                throw new InvalidOperationException("Invalid hash algorithm: " + hashAlgorithmName);
                        }
                    }

                    if (i > 0)
                    {
                        WriteLine("{0}\tattempt #{1}\t after {2}s", name, i + 1, delay.TotalSeconds);
                        await Task.Delay(delay);
                    }
                    else
                    {
                        WriteLine("{0}\tdownloading from \"{1}\"", name, sourceUrl);
                    }

                    if (await DownloadFile(name, sourceUrl, destinationFile))
                    {
                        if (hashAlgorithm != null)
                        {
                            using (FileStream fileStream = File.OpenRead(destinationFile))
                            {
                                byte[] computedHash = hashAlgorithm.ComputeHash(fileStream);
                                bool hashIsEqual = StructuralComparisons.StructuralEqualityComparer.Equals(hash, computedHash);
                                if (!hashIsEqual)
                                {
                                    StringBuilder computedHashStr = new StringBuilder(computedHash.Length * 2);
                                    foreach (byte b in computedHash)
                                    {
                                        computedHashStr.AppendFormat("{0:x2}", b);
                                    }

                                    throw new InvalidOperationException("Hashes are not match. Expected: " + hashValue + ". Actual: " + computedHashStr);
                                }

                                WriteLine(destinationFile + " checksum(" + hashAlgorithmName + ") " + hashValue + " is valid.");
                            }
                        }

                        success = true;
                        break;
                    }

                    WriteErrorLine("{0}\tabort", name);
                }
                catch (Exception ex)
                {
                    WriteErrorLine("{0}\terror: \"{1}\"", name, ex.Message);
                    if (ex is InvalidOperationException)
                    {
                        throw;
                    }
                }
            }

            if (!success)
            {
                throw new Exception(sourceUrl + " - download failed.");
            }

            return success;
        }

        /// <summary>
        /// Downloads file using DotNet's WebClient.
        /// </summary>
        /// <param name="name">file name (used for logging purposes)</param>
        /// <param name="sourceUrl">URL of the file to be downloaded</param>
        /// <param name="destinationFile">destination path to which the file should be downloaded</param>
        /// <returns>the result of async task's execution</returns>
        private static async Task<bool> DownloadFile(string name, string sourceUrl, string destinationFile)
        {
            Uri source = new Uri(sourceUrl);
            string host = source.DnsSafeHost;
            if (string.IsNullOrWhiteSpace(host))
            {
                throw new InvalidOperationException("Cannot get dns name for " + sourceUrl);
            }

            WriteLine("{0}\tresolving \"{1}\" by dns", name, source.DnsSafeHost);
            IPHostEntry hostInfo = await Dns.GetHostEntryAsync(host);
            foreach (var address in hostInfo.AddressList)
            {
                WriteLine("{0}\t address {1}", host, address);
            }

            using (WebClient client = new WebClient())
            {
                Stopwatch stopwatch = new Stopwatch();
                stopwatch.Start();
                client.DownloadProgressChanged += (sender, args) =>
                    {
                        long percent = 100 * args.BytesReceived / args.TotalBytesToReceive;
                        if (percent % 5 == 0 && percent > lastPercent)
                        {
                            double speed = args.TotalBytesToReceive / 1024.0 / 1024.0 / stopwatch.Elapsed.TotalSeconds;
                            if (lastPercent == -1)
                            {
                                speed = 0;
                            }

                            lastPercent = percent;
                            WriteLine("\t{0}%\t{1}\t{2:0.0} MB/s", percent, name, speed);
                        }
                };

                bool completed = false;
                client.DownloadFileCompleted += (sender, args) =>
                {
                    completed = true;
                };

                try {
                    await client.DownloadFileTaskAsync(source, destinationFile);
                    return completed;
                } catch (Exception fileDownloadException) {
                    // Please, note that "AccessDenied"-related exception might occur in case of ...
                    // ... (1) FileSystem limitation - destination file couldn't be created / sub-folder does not exist;
                    // ... (2) Network limitation - host's network settings (proxy, local DNS) are blocking the file download;
                    while (fileDownloadException != null) {
                        WriteErrorLine(fileDownloadException.Message);
                        fileDownloadException = fileDownloadException.InnerException;
                    }
                    throw fileDownloadException;
                }
            }
        }

        private static void WriteLine(string message, params object[] args)
        {
            lock (LockObject)
            {
                Console.WriteLine(message, args);
            }
        }

        private static void WriteErrorLine(string message, params object[] args)
        {
            lock (LockObject)
            {
                Console.Error.WriteLine(message, args);
            }
        }
    }
}
