package com.zhangzhu95.retrofit.services

import com.zhangzhu95.data.movies.models.MovieDetails
import com.zhangzhu95.data.movies.models.MoviesListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MoviesService {

    @GET("trending/all/day")
    suspend fun getTrending(): Response<MoviesListResponse>

    @GET("movie/top_rated")
    suspend fun getTopRated(): Response<MoviesListResponse>

    @GET("movie/{id}")
    suspend fun getDetails(@Path("id") id: String): Response<MovieDetails>
}
