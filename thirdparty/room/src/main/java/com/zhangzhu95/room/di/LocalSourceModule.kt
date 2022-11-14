package com.zhangzhu95.room.di

import com.zhangzhu95.data.movies.MoviesLocalSource
import com.zhangzhu95.data.search.SearchLocalSource
import com.zhangzhu95.room.data.MoviesLocalSourceImpl
import com.zhangzhu95.room.data.SearchLocalSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class LocalSourceModule {

    @Binds
    abstract fun provideMoviesLocalSource(
        moviesLocalSourceImpl: MoviesLocalSourceImpl
    ): MoviesLocalSource

    @Binds
    abstract fun provideSearchHistoryLocalSource(
        searchLocalSourceImpl: SearchLocalSourceImpl
    ): SearchLocalSource
}
