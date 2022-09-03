package com.zhangzhu95.data.movies

import com.zhangzhu95.data.movies.models.MovieDetails
import com.zhangzhu95.data.movies.models.MoviesListResponse
import com.zhangzhu95.data.networking.Response

interface MoviesRemoteSource {

    suspend fun getTrending(): Response<MoviesListResponse>

    suspend fun getTopRated(): Response<MoviesListResponse>

    suspend fun getDetails(id: String): Response<MovieDetails>
}
