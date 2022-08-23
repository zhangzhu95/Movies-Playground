package com.zhangzhu95.retrofit.data

import com.zhangzhu95.core.data.networking.Response
import com.zhangzhu95.core.data.networking.details.DetailsRemoteSource
import com.zhangzhu95.core.data.networking.details.MovieDetails
import com.zhangzhu95.core.data.networking.trending.TrendingAll
import com.zhangzhu95.core.data.networking.trending.TrendingRemoteSource
import com.zhangzhu95.retrofit.services.DetailsService
import com.zhangzhu95.retrofit.services.TrendingService
import javax.inject.Inject

class DetailsRemoteSourceImpl @Inject constructor(
    private val service: DetailsService
) : DetailsRemoteSource, RemoteDataSource {
    override suspend fun getDetails(id: String): Response<MovieDetails> = getBasicResult {
        service.getDetails(id)
    }
}
