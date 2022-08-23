package com.zhangzhu95.core.data.networking.details

import com.zhangzhu95.core.data.networking.Response

interface DetailsRemoteSource {
    suspend fun getDetails(id: String): Response<MovieDetails>
}
