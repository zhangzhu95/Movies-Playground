package com.zhangzhu95.retrofit.data

import com.zhangzhu95.data.actors.ActorsRemoteSource
import com.zhangzhu95.retrofit.services.ActorsService
import javax.inject.Inject

class ActorsRemoteDataSourceImpl @Inject constructor(
    private val service: ActorsService
) : ActorsRemoteSource, RemoteDataSource {

    override suspend fun getCredits(movieId: String) = getBasicResult {
        service.getCredits(movieId)
    }
}
