package com.zhangzhu95.data.networking

sealed class Response<R> {
    data class Success<R>(val data: R?) : Response<R>()
    data class Error<R>(val message: String) : Response<R>()
}
