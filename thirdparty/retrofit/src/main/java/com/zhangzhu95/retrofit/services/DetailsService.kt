package com.zhangzhu95.retrofit.services

import com.zhangzhu95.core.data.networking.details.MovieDetails
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface DetailsService {

    @GET("movie/{id}")
    suspend fun getDetails(@Path("id") id: String): Response<MovieDetails>
}
