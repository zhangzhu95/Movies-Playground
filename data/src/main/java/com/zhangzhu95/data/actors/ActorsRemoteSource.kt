package com.zhangzhu95.data.actors

import com.zhangzhu95.core.networking.Response
import com.zhangzhu95.data.movies.models.CastResponse

interface ActorsRemoteSource {

    suspend fun getCredits(movieId: String): Response<CastResponse>
}
