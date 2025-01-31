package com.example.objectdetection

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class ResponseInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)

        if (!response.isSuccessful) {
            val errorMessage = when (response.code) {
                400 -> "Bad Request: The request was unacceptable, often due to missing a required parameter"
                401 -> "Unauthorized: Invalid Access Token"
                403 -> "Forbidden: Missing permissions to perform request"
                404 -> "Not Found: The requested resource does not exist."
                in 500..503 -> "Server Error: An error occurred on the server side. Code: ${response.code}"
                else -> "Unexpected response code: ${response.code}. Message: ${response.message}"
            }
            throw IOException(errorMessage)
        }

        return response
    }

}