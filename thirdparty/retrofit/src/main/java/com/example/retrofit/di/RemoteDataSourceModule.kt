package com.example.retrofit.di

import com.example.core.data.networking.trending.TrendingRemoteSource
import com.example.retrofit.data.TrendingRemoteSourceImpl
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