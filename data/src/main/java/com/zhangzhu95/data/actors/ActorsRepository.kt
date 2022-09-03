package com.zhangzhu95.data.actors

import javax.inject.Inject

class ActorsRepository @Inject constructor(
    private val actorsRemoteSource: ActorsRemoteSource
) {

    suspend fun getCredits(movieId: String) = actorsRemoteSource.getCredits(movieId)
}
