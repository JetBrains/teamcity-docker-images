namespace Scripts
{
    public class Program
    {
        public static void Main()
        {
             Web.DownloadFiles(
                 @"https://corretto.aws/downloads/resources/11.0.9.11.2/amazon-corretto-11.0.9.11.2-windows-x64-jdk.zip#MD5#9757ebcd8094b4d7040886b2c98bcf37",
                 @"jdk.zip",
                 @"https://corretto.aws/downloads/resources/8.252.09.2/amazon-corretto-8.252.09.2-windows-x64-jre.zip",
                 @"jre.zip");
        }
    }
}
