package com.zhangzhu95.retrofit.interceptors

import com.zhangzhu95.core.config.Config
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader("Authorization", "Bearer ${Config.API_ACCESS_TOKEN}")
            .build()
        return chain.proceed(request)
    }
}
