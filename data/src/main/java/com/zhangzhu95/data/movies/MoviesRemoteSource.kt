package com.zhangzhu95.data.movies

import com.zhangzhu95.core.networking.Response
import com.zhangzhu95.data.movies.models.MovieDetails
import com.zhangzhu95.data.movies.models.MoviesListResponse

interface MoviesRemoteSource {

    suspend fun getTrending(): Response<MoviesListResponse>

    suspend fun getTopRated(): Response<MoviesListResponse>

    suspend fun getDetails(id: String): Response<MovieDetails>

    suspend fun getUpcoming(): Response<MoviesListResponse>

    suspend fun searchMovies(query: String, page: Int): Response<MoviesListResponse>
}
