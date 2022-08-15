package com.example.retrofit.services

import com.example.core.data.networking.trending.TrendingAll
import retrofit2.Response
import retrofit2.http.GET

interface TrendingService {

    @GET("trending/all/day")
    suspend fun getTrending(): Response<TrendingAll>
}
