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

    private val client: HttpClient = HttpClient.newHttpClient()

    /**
     * Checks if an HTTP response had succeeded (has any of 200 codes)
     * @param response HTTP response to be checked
     * @return true if status is in range of [200; 299]
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
        val requestConfig = HttpRequest.newBuilder()
                                                            .uri(URI.create(uri))
                                                            .header("Accept", "application/json")
        if (token != null) {
            requestConfig.header("Authorization", "Bearer $token")
        }
        val request = requestConfig.GET().build()
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