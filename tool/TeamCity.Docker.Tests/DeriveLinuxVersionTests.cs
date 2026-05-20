namespace TeamCity.Docker.Tests
{
    using System.Collections.Generic;
    using System.Linq;
    using Shouldly;
    using Xunit;

    public class DeriveLinuxVersionTests
    {
        private static List<(string buildPath, string configFile, IReadOnlyDictionary<string, string> rawVars)>
            MakeVariants(params (string configPath, string linuxVersion)[] items)
        {
            return items.Select(item =>
            {
                var vars = new Dictionary<string, string> { ["linuxVersion"] = item.linuxVersion };
                return ("build", item.configPath, (IReadOnlyDictionary<string, string>)vars);
            }).ToList();
        }

        [Fact]
        public void LatestVersionGetsNoSuffix()
        {
            var variants = MakeVariants(
                ("configs/linux/Server/Ubuntu/20.04/Ubuntu.Dockerfile.config", ""),
                ("configs/linux/Server/Ubuntu/22.04/Ubuntu.Dockerfile.config", ""),
                ("configs/linux/Server/Ubuntu/24.04/Ubuntu.Dockerfile.config", ""));

            ConfigurationExplorer.DeriveLinuxVersionInRawVars(variants);

            variants[0].rawVars["linuxVersion"].ShouldBe("20.04");
            variants[1].rawVars["linuxVersion"].ShouldBe("22.04");
            variants[2].rawVars["linuxVersion"].ShouldBe(""); // latest — unchanged
        }

        [Fact]
        public void ArmVersionsGetSuffixAppended()
        {
            var variants = MakeVariants(
                ("configs/linux/Agent/UbuntuARM/20.04/UbuntuARM.Dockerfile.config", "arm64"),
                ("configs/linux/Agent/UbuntuARM/22.04/UbuntuARM.Dockerfile.config", "arm64"),
                ("configs/linux/Agent/UbuntuARM/24.04/UbuntuARM.Dockerfile.config", "arm64"));

            ConfigurationExplorer.DeriveLinuxVersionInRawVars(variants);

            variants[0].rawVars["linuxVersion"].ShouldBe("arm64-20.04");
            variants[1].rawVars["linuxVersion"].ShouldBe("arm64-22.04");
            variants[2].rawVars["linuxVersion"].ShouldBe("arm64"); // latest — unchanged
        }

        [Fact]
        public void ExplicitVersionIsNotDuplicated()
        {
            // 18.04 configs already have the version in linuxVersion
            var variants = MakeVariants(
                ("configs/linux/Agent/Ubuntu/18.04/Ubuntu.Dockerfile.config", "18.04"),
                ("configs/linux/Agent/Ubuntu/24.04/Ubuntu.Dockerfile.config", ""));

            ConfigurationExplorer.DeriveLinuxVersionInRawVars(variants);

            variants[0].rawVars["linuxVersion"].ShouldBe("18.04"); // already contains version — unchanged
            variants[1].rawVars["linuxVersion"].ShouldBe(""); // latest — unchanged
        }

        [Fact]
        public void SudoDirectoryVersionIsExtractedCorrectly()
        {
            var variants = MakeVariants(
                ("configs/linux/Agent/Ubuntu/20.04-sudo/Ubuntu-sudo.Dockerfile.config", ""),
                ("configs/linux/Agent/Ubuntu/24.04-sudo/Ubuntu-sudo.Dockerfile.config", ""));

            ConfigurationExplorer.DeriveLinuxVersionInRawVars(variants);

            variants[0].rawVars["linuxVersion"].ShouldBe("20.04");
            variants[1].rawVars["linuxVersion"].ShouldBe(""); // latest — unchanged
        }

        [Fact]
        public void EmptyVariantsListDoesNotThrow()
        {
            var variants = new List<(string buildPath, string configFile, IReadOnlyDictionary<string, string> rawVars)>();
            ConfigurationExplorer.DeriveLinuxVersionInRawVars(variants);
            variants.ShouldBeEmpty();
        }

        [Fact]
        public void VariantsWithoutLinuxVersionAreSkipped()
        {
            var vars = new Dictionary<string, string> { ["someOtherVar"] = "value" };
            var variants = new List<(string buildPath, string configFile, IReadOnlyDictionary<string, string> rawVars)>
            {
                ("build", "configs/devkit/linux/server/ubuntu/Ubuntu.devkit.Dockerfile.config", vars)
            };

            ConfigurationExplorer.DeriveLinuxVersionInRawVars(variants);

            variants[0].rawVars.ContainsKey("linuxVersion").ShouldBeFalse();
            variants[0].rawVars["someOtherVar"].ShouldBe("value");
        }

        [Fact]
        public void SingleVersionIsLatest()
        {
            var variants = MakeVariants(
                ("configs/linux/Server/Ubuntu/24.04/Ubuntu.Dockerfile.config", ""));

            ConfigurationExplorer.DeriveLinuxVersionInRawVars(variants);

            variants[0].rawVars["linuxVersion"].ShouldBe(""); // only version = latest
        }
    }
}
