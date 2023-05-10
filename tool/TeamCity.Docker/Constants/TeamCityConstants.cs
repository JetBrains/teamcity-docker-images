namespace TeamCity.Docker.Constants
{
    /// <summary>
    /// Describes TeamCity-related constants
    /// </summary>
    public static class TeamCityConstants
    {
        /// <summary>
        /// Failure conditions 
        /// </summary>
        public static class Conditions {
            public const string REGEXP = "BuildFailureOnText.ConditionType.REGEXP";
        }

        /// <summary>
        /// Build Configurations inside of TeamCIty that're referenced within the code 
        /// </summary>
        public static class TrunkConfigurations {
            public const string BUILD_DIST_DOCKER = "TC2023_05_BuildDistDocker";
        }
    }
}
