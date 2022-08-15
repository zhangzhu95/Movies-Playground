package com.zhangzhu95.retrofit.services

import com.zhangzhu95.core.data.networking.trending.TrendingAll
import retrofit2.Response
import retrofit2.http.GET

interface TrendingService {

    @GET("trending/all/day")
    suspend fun getTrending(): Response<TrendingAll>
}
