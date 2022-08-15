package com.zhangzhu95.retrofit.data

import com.zhangzhu95.core.data.networking.Response
import com.zhangzhu95.core.data.networking.trending.TrendingAll
import com.zhangzhu95.core.data.networking.trending.TrendingRemoteSource
import com.zhangzhu95.retrofit.services.TrendingService
import javax.inject.Inject

class TrendingRemoteSourceImpl @Inject constructor(
    private val service: TrendingService
) : TrendingRemoteSource, RemoteDataSource {
    override suspend fun getTrending(): Response<TrendingAll> = getBasicResult {
        service.getTrending()
    }
}
