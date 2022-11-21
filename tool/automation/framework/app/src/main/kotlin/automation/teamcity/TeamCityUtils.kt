package automation.teamcity

class TeamCityUtils {
    companion object {
        /**
         * Reports statistics to TeamCity via Service Messages.
         * @param key metric ID
         * @param value metricValue
         *
         * TODO: Think about generic 'value' type
         */
        fun reportTeamCityStatistics(key: String, value: Any) {
            println("##teamcity[buildStatisticValue key='$key' value='$value']")
        }
    }
}
