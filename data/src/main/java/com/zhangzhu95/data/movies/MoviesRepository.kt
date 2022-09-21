package com.zhangzhu95.data.movies

import com.zhangzhu95.data.movies.models.MovieHistory
import javax.inject.Inject

class MoviesRepository @Inject constructor(
    private val localDataSource: MoviesLocalSource,
    private val remoteDataSource: MoviesRemoteSource
) {

    suspend fun fetchTrendingAll() = remoteDataSource.getTrending()

    suspend fun fetchTopRated() = remoteDataSource.getTopRated()

    suspend fun fetchDetails(id: String) = remoteDataSource.getDetails(id)

    suspend fun fetchUpcoming() = remoteDataSource.getUpcoming()

    suspend fun searchMovies(query: String, page: Int) = remoteDataSource.searchMovies(query, page)

    suspend fun addToHistory(movie: MovieHistory) = localDataSource.addToHistory(movie)

    suspend fun getMoviesHistory() = localDataSource.getMoviesHistory()
}
