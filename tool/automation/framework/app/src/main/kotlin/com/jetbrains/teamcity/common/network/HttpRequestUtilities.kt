package com.jetbrains.teamcity.common.network

import java.net.HttpURLConnection
import java.net.URL
import java.net.http.HttpRequest

class HttpRequestUtilities {
    companion object {
        fun performRequest(url: String): String? {
            val targetUrl = URL(url)
            val http: HttpURLConnection = targetUrl.openConnection() as HttpURLConnection
            http.requestMethod = "GET"
            http.connectTimeout = 10*1000
            http.readTimeout = 30*1000
            http.doOutput = true

            val body = String(http.inputStream.readAllBytes())
            return body
        }
    }
}