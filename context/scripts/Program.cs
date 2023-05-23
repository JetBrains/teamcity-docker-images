namespace Scripts
{
    public class Program
    {
        public static void Main()
        {
             Web.DownloadFiles(
                 @"https://corretto.aws/downloads/resources/17.0.7.7.1/amazon-corretto-17.0.7.7.1-windows-x64-jdk.zip#MD5#feb7eab99c647a0b4347be9f0a3276de",
                 @"jdk.zip",
                 // TODO: Replace mentions of JRE within NanoServer Docker manifests with JDK, which is actually used, given ...
                 // ... missing Amazon Corretto's JRE 17 for Windows within official releases for Windows.
                 @"https://corretto.aws/downloads/resources/17.0.7.7.1/amazon-corretto-17.0.7.7.1-windows-x64-jdk.zip#MD5#feb7eab99c647a0b4347be9f0a3276de",
                 @"jre.zip");
        }
    }
}
