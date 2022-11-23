package com.jetbrains.teamcity.common.network

import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

/**
 * Utilities for HTTP requests.
 */
class HttpRequestUtilities {
    companion object {
        private const val DEFAULT_CONN_TIMEOUT_MILLIS = 10*1000
        private const val DEFAULT_READ_TIMEOUT_MILLIS = 30*1000

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
                httpURLConnection.readTimeout = DEFAULT_READ_TIMEOUT_MILLIS

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
    }
}
