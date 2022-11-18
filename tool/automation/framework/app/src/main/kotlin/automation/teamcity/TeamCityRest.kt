package automation.teamcity

import java.lang.System
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

/**
 * Utilities for TeamCity REST API
 */
class TeamCityRest {
    companion object {
        fun getLastReleaseNumber(name: String): String {

            val client = HttpClient.newBuilder().build();
            val serverUrl = java.lang.System.getProperty("teamcity.serverUrl")
            val request = HttpRequest.newBuilder()
                .uri(URI.create("$serverUrl/app/rest/projects"))
                .header("Accept", "application/json")
                .build();

            val response = client.send(request, HttpResponse.BodyHandlers.ofString());
            println(response.body())
            return response.body()
        }
    }
}