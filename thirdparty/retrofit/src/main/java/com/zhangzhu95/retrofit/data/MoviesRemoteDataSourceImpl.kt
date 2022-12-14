package com.zhangzhu95.retrofit.data

import com.zhangzhu95.core.networking.Response
import com.zhangzhu95.data.movies.MoviesRemoteSource
import com.zhangzhu95.data.movies.models.MovieDetails
import com.zhangzhu95.data.movies.models.MoviesListResponse
import com.zhangzhu95.retrofit.services.MoviesService
import javax.inject.Inject

class MoviesRemoteDataSourceImpl @Inject constructor(
    private val service: MoviesService
) : MoviesRemoteSource, RemoteDataSource {
    override suspend fun getTrending(): Response<MoviesListResponse> = getBasicResult {
        service.getTrending()
    }

    override suspend fun getTopRated(): Response<MoviesListResponse> = getBasicResult {
        service.getTopRated()
    }

    override suspend fun getDetails(id: String): Response<MovieDetails> = getBasicResult {
        service.getDetails(id)
    }

    override suspend fun getUpcoming(): Response<MoviesListResponse> = getBasicResult {
        service.getUpcoming()
    }

    override suspend fun searchMovies(query: String, page: Int): Response<MoviesListResponse> =
        getBasicResult {
            service.searchMovies(query, page)
        }
}
