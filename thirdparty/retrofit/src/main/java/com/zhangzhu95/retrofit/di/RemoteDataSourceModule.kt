package com.zhangzhu95.retrofit.di

import com.zhangzhu95.core.data.networking.trending.TrendingRemoteSource
import com.zhangzhu95.retrofit.data.TrendingRemoteSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class RemoteDataSourceModule {
    @Binds
    abstract fun trendingService(trendingRemoteSourceImpl: TrendingRemoteSourceImpl): TrendingRemoteSource
}