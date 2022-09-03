package com.zhangzhu95.data.actors

import com.zhangzhu95.data.movies.models.CastResponse
import com.zhangzhu95.data.networking.Response

interface ActorsRemoteSource {

    suspend fun getCredits(movieId: String): Response<CastResponse>
}
