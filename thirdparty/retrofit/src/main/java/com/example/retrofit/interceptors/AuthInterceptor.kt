package com.example.retrofit.interceptors

import com.example.core.config.Config
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer ${Config.API_ACCESS_TOKEN}").build()
        val response = chain.proceed(request)
        return response
    }
}
