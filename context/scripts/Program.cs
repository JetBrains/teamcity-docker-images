namespace Scripts
{
    public class Program
    {
        public static void Main()
        {
             Web.DownloadFiles(
                 @"https://corretto.aws/downloads/resources/17.0.7.7.1/amazon-corretto-17.0.7.7.1-windows-x64-jdk.zip#MD5#feb7eab99c647a0b4347be9f0a3276de",
                 @"jdk.zip",
                 // TODO: Add JRE 17
                 @"https://corretto.aws/downloads/resources/8.252.09.2/amazon-corretto-8.252.09.2-windows-x64-jre.zip",
                 @"jre.zip");
        }
    }
}
