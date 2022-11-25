package com.jetbrains.teamcity.common.network

import java.io.IOException
import java.net.ConnectException
import java.net.HttpURLConnection
import java.net.URI
import java.net.URL
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

/**
 * Utilities for HTTP requests.
 */
class HttpRequestsUtilities {

    val client: HttpClient = HttpClient.newHttpClient()

    companion object {
        private const val DEFAULT_CONN_TIMEOUT_MILLIS = 10*1000
        private const val DEFAULT_READ_TIMEOUT_MILLIS = 30*1000
    }

    /**
     * Performs HTTP GET request.
     * @param url target URI
     * @return requests' body, null if the request did not succeed
     */
    fun performGetRequest(url: String): String? {
        var responseBody: String? = null
        var httpURLConnection: HttpURLConnection? = null

        try {
            val targetUrl = URL(url)
            httpURLConnection = targetUrl.openConnection() as HttpURLConnection
            httpURLConnection.requestMethod = "GET"
            httpURLConnection.doOutput = true

            httpURLConnection.connectTimeout = DEFAULT_CONN_TIMEOUT_MILLIS
            httpURLConnection.readTimeout = Companion.DEFAULT_READ_TIMEOUT_MILLIS

            httpURLConnection.inputStream.use { httpInputStream ->
                responseBody = String(httpInputStream.readAllBytes())
            }
        } catch (err: Exception) {
            System.err.println("An error had occurred while executing HTTP GET request: $err \n URL: $url")
            responseBody = null
        } finally {
            httpURLConnection?.disconnect()
        }

        return responseBody
    }

    /**
     * Checks if an HTTP response had succeeded (has any of 200 codes)
     */
    fun isResponseSuccessful(response: HttpResponse<String?>): Boolean {
        // '200' codes
        return ((response.statusCode() % 200) < 100)
    }


    /**
     * Send HTTP GET request and receive JSON response as a result.
     * Purpose: in context of automation, the operation is frequent.
     * @param uri - target URI
     * @param token (optional) - access token (Bearer)
     * @return HTTP response object
     */
    @Throws(InterruptedException::class)
    fun getJsonWithAuth(
        uri: String?,
        token: String? = null
    ): HttpResponse<String?> {
        val request = HttpRequest.newBuilder()
            .uri(URI.create(uri))
            .header("Accept", "application/json")
            // token is nullable
            .header("Authorization", "Bearer $token")
            .GET()
            .build()
        return performHttpRequest(request)
    }

    /**
     * Performs generic HTTP request.
     * The logic had been moved into a separate method in order to enclose the logic related to exception handling, etc.
     * Purpose: encapsulate logic of creation handles, logging, etc.
     * @param request - intended HTTP request
     * @return object representing HTTP resounce
     * @throws InterruptedException - timeout of HTTP request;
     */
    @Throws(InterruptedException::class)
    private fun performHttpRequest(request: HttpRequest): HttpResponse<String?> {
        return try {
            client.send(request, HttpResponse.BodyHandlers.ofString())
        } catch (e: ConnectException) {
            throw RuntimeException("Server is not reachable at URI " + request.uri())
        } catch (e: InterruptedException) {
            throw InterruptedException("HTTP method timed out. " + e.message)
        } catch (e: IOException) {
            throw RuntimeException("Unable to perform HTTP request for URI " + request.uri())
        }
    }

}