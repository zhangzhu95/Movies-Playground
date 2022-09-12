package com.zhangzhu95.data.movies

import javax.inject.Inject

class MoviesRepository @Inject constructor(private val remoteDataSource: MoviesRemoteSource) {

    suspend fun fetchTrendingAll() = remoteDataSource.getTrending()

    suspend fun fetchTopRated() = remoteDataSource.getTopRated()

    suspend fun fetchDetails(id: String) = remoteDataSource.getDetails(id)

    suspend fun fetchUpcoming() = remoteDataSource.getUpcoming()
}
