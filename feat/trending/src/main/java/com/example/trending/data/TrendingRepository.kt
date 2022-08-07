package com.example.trending.data

import com.example.core.data.networking.trending.TrendingRemoteSource
import javax.inject.Inject

class TrendingRepository @Inject constructor(private val remoteDataSource: TrendingRemoteSource) {
    suspend fun fetchTrendingAll() = remoteDataSource.getTrending()
}