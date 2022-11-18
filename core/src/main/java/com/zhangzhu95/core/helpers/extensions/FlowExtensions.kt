package com.zhangzhu95.core.helpers.extensions

import com.zhangzhu95.core.networking.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach

/*
A custom operator that call the callback passed in the argument when the Response is Successful
 */

inline fun <reified R> Flow<Response<R>>.doOnSuccess(
    crossinline callback: suspend (Response.Success<R>) -> Unit
): Flow<Response<R>> =
    this.onEach {
        if (it is Response.Success) {
            callback(it)
        }
    }