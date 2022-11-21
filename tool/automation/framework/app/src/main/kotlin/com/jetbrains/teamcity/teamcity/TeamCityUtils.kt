package com.jetbrains.teamcity.teamcity

class TeamCityUtils {
    companion object {
        /**
         * Reports statistics to TeamCity via Service Messages.
         * @param key metric ID
         * @param value metricValue
         *
         */
        fun reportTeamCityStatistics(key: String, value: Any) {
            println("##teamcity[buildStatisticValue key='$key' value='$value']")
        }
    }
}
