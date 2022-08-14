package com.example.retrofit.data

import com.example.core.data.networking.Response
import timber.log.Timber

interface RemoteDataSource {

    suspend fun <T> getBasicResult(call: suspend () -> retrofit2.Response<T>): Response<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                Timber.i("Request Response Success : ${response.body().toString()}")
                val body = response.body()
                if (body != null)
                    return Response.Success(body)
            }
            return error(response.message())
        } catch (e: Exception) {
            return error(e.message.orEmpty())
        }

    }

    private fun <T> error(message: String): Response.Error<T> {
        Timber.e("Request Response Failure ss : $message")
        return Response.Error(message)
    }
}