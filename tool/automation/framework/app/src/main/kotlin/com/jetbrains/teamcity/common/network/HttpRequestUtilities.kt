package com.jetbrains.teamcity.common.network

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
         */
        fun performGetRequest(url: String): String? {
            val targetUrl = URL(url)
            val http: HttpURLConnection = targetUrl.openConnection() as HttpURLConnection
            http.requestMethod = "GET"
            http.doOutput = true

            http.connectTimeout = DEFAULT_CONN_TIMEOUT_MILLIS
            http.readTimeout = DEFAULT_READ_TIMEOUT_MILLIS

            val responseBody = String(http.inputStream.readAllBytes())
            return responseBody
        }
    }
}