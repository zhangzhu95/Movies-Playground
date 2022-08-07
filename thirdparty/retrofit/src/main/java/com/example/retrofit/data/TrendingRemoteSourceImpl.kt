package com.example.retrofit.data

import com.example.core.data.networking.Response
import com.example.core.data.networking.trending.TrendingAll
import com.example.core.data.networking.trending.TrendingRemoteSource
import com.example.retrofit.services.TrendingService
import javax.inject.Inject

class TrendingRemoteSourceImpl @Inject constructor(
    private val service: TrendingService
) : TrendingRemoteSource, RemoteDataSource {
    override suspend fun getTrending(): Response<TrendingAll> = getBasicResult {
        service.getTrending()
    }
}