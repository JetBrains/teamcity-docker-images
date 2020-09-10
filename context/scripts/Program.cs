namespace Scripts
{
    public class Program
    {
        public static void Main()
        {
             Web.DownloadFiles(
                 @"https://corretto.aws1/downloads/resources/8.252.09.2/amazon-corretto-8.252.09.2-windows-x64-jdk.zip",
                 @"jdk.zip",
                 @"https://corretto.aws/downloads/resources/8.252.09.2/amazon-corretto-8.252.09.2-windows-x64-jre.zip",
                 @"jre.zip");
        }
    }
}
