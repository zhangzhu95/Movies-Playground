package com.zhangzhu95.core.data.networking.trending

import com.zhangzhu95.core.data.networking.Response

interface TrendingRemoteSource {
    suspend fun getTrending(): Response<TrendingAll>
}