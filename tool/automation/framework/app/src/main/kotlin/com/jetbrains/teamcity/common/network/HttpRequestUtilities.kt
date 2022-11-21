package com.jetbrains.teamcity.common.network

import java.net.HttpURLConnection
import java.net.URL

class HttpRequestUtilities {
    companion object {
        fun performRequest(url: String): String? {
            val targetUrl = URL(url)
            val con: HttpURLConnection = targetUrl.openConnection() as HttpURLConnection
            con.requestMethod = "GET"
            con.connectTimeout = 10*1000
            con.readTimeout = 30*1000
            con.doOutput = true
//            con.addRequestProperty("User-Agent", "Rawr/1.0")

            val status = con.responseCode
            val statusMsg = con.responseMessage
            val headers = con.headerFields

            val contentType = con.contentType
            val body = String(con.inputStream.readAllBytes())
            return body
//            println(body)

        }
    }
}