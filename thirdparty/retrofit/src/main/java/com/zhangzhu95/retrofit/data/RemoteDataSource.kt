package com.zhangzhu95.retrofit.data

import com.zhangzhu95.core.data.networking.Response
import timber.log.Timber

@Suppress("TooGenericExceptionCaught")
interface RemoteDataSource {

    suspend fun <T> getBasicResult(call: suspend () -> retrofit2.Response<T>): Response<T> {
        val error = try {
            val response = call()
            if (response.isSuccessful) {
                Timber.i("Request Response Success : ${response.body()}")
                val body = response.body()
                if (body != null)
                    return Response.Success(body)
            }

            response.message()
        } catch (e: Exception) {
            e.message.orEmpty()
        }

        return error(error)
    }

    private fun <T> error(message: String): Response.Error<T> {
        Timber.e("Request Response Failure ss : $message")
        return Response.Error(message)
    }
}
