package com.zhangzhu95.retrofit.services

import com.zhangzhu95.data.movies.models.CastResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ActorsService {

    @GET("movie/{id}/credits")
    suspend fun getCredits(@Path("id") id: String): Response<CastResponse>
}
