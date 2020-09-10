using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.IO;
using System.Net;
using System.Threading.Tasks;

namespace Scripts
{
    public class Web
    {
        private static readonly object LockObject = new object();

        public static int DownloadFiles(params string[] args)
        {
            List<Task> tasks = new List<Task>();
            for (int i = 0; i < args.Length / 2; i++)
            {
                tasks.Add(DownloadFile(args[i * 2], args[i * 2 + 1], 300, TimeSpan.FromSeconds(10)));
            }

            Task.WhenAll(tasks).Wait();
            return 0;
        }

        private static async Task DownloadFile(string sourceUrl, string destinationFile, int attempts, TimeSpan delay)
        {
            string name = Path.GetFileName(destinationFile);
            bool success = false;
            for (int i = 0; i < attempts; i++)
            {
                try
                {
                    if (i > 0)
                    {
                        WriteLine("{0}\tattempt #{1}\t after {2}s", name, i + 1, delay.TotalSeconds);
                        await Task.Delay(delay);
                    }
                    else
                    {
                        WriteLine("{0}\tdownloading", name);
                    }

                    success = await DownloadFile(name, sourceUrl, destinationFile);
                    if (success)
                    {
                        break;
                    }

                    WriteErrorLine("{0}\tabort", name);
                }
                catch (Exception ex)
                {
                    WriteErrorLine("{0}\terror: \"{1}\"", name, ex.Message);
                }
            }

            if (!success)
            {
                throw new Exception(sourceUrl + " - download failed.");
            }
        }

        private static async Task<bool> DownloadFile(string name, string sourceUrl, string destinationFile)
        {
            using (WebClient client = new WebClient())
            {
                long lastPercent = -1;
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

                await client.DownloadFileTaskAsync(new Uri(sourceUrl), destinationFile);
                return completed;
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
