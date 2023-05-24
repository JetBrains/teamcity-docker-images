namespace Scripts
{
    public class Program
    {
        public static void Main()
        {
            // Download JDK archive to include it into the Docker Containers during the build time.
             Web.DownloadFiles(
                 @"https://corretto.aws/downloads/resources/17.0.7.7.1/amazon-corretto-17.0.7.7.1-windows-x64-jdk.zip#MD5#feb7eab99c647a0b4347be9f0a3276de",
                 @"jdk.zip");
        }
    }
}
