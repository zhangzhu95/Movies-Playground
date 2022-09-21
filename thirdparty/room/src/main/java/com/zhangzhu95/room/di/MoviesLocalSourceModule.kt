package com.zhangzhu95.room.di

import com.zhangzhu95.data.movies.MoviesLocalSource
import com.zhangzhu95.room.data.MoviesLocalSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class MoviesLocalSourceModule {

    @Binds
    abstract fun provideMoviesLocalSource(
        moviesLocalSourceImpl: MoviesLocalSourceImpl
    ): MoviesLocalSource
}
