package com.zhangzhu95.trending.data

import com.zhangzhu95.core.data.networking.trending.TrendingRemoteSource
import javax.inject.Inject

class TrendingRepository @Inject constructor(private val remoteDataSource: TrendingRemoteSource) {
    suspend fun fetchTrendingAll() = remoteDataSource.getTrending()
}