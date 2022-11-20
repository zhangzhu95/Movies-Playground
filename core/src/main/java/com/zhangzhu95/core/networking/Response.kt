package com.zhangzhu95.core.networking


sealed interface Response<in R> {
    data class Success<R>(val data: R?) : Response<R>
    data class Error<R>(val message: String) : Response<R>
    object Loading: Response<Any>
}
