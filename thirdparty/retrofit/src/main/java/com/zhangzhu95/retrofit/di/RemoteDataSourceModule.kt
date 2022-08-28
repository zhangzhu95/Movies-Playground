package com.zhangzhu95.retrofit.di

import com.zhangzhu95.data.movies.MoviesRemoteSource
import com.zhangzhu95.retrofit.data.MoviesRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class RemoteDataSourceModule {
    @Binds
    abstract fun moviesService(source: MoviesRemoteDataSourceImpl): MoviesRemoteSource
}
