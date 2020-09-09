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
        public static int DownloadFiles(params string[] args)
        {
            List<Task> tasks = new List<Task>();
            for (int i = 0; i < args.Length / 2; i++)
            {
                tasks.Add(DownloadFile(args[i * 2], args[i * 2 + 1], 3));
            }

            Task.WhenAll(tasks).Wait();
            return 0;
        }

        private static async Task DownloadFile(string sourceUrl, string destinationFile, int attempts)
        {
            string name = Path.GetFileName(destinationFile);
            bool success = false;
            for (int i = 0; i < attempts; i++)
            {
                try
                {
                    if (i > 0)
                    {
                        Console.WriteLine("     {0} attempt #{1}", name, i + 1);
                    }

                    success = await DownloadFile(name, sourceUrl, destinationFile);
                    if (success)
                    {
                        break;
                    }

                    Console.Error.WriteLine("   {0} abort", name);
                }
                catch (Exception ex)
                {
                    Console.Error.WriteLine("   {0} error {1}", name, ex.Message);
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
                        Console.WriteLine(" {0:00}% {1} {2:0.0} MB/s", percent, name, speed);
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
    }
}
