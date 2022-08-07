package com.example.retrofit.data

import android.util.Log
import com.example.core.data.networking.Response

interface RemoteDataSource {

    suspend fun <T> getBasicResult(call: suspend () -> retrofit2.Response<T>): Response<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                Log.i("request", "Request Response Success : ${response.body().toString()}")
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
        Log.e("request", "Request Response Failure ss : $message")
        return Response.Error(message)
    }
}