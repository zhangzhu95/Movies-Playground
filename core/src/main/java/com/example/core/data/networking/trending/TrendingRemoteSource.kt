package com.example.core.data.networking.trending

import com.example.core.data.networking.Response

interface TrendingRemoteSource {
    suspend fun getTrending(): Response<TrendingAll>
}