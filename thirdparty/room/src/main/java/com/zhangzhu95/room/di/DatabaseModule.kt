package com.zhangzhu95.room.di

import android.content.Context
import androidx.room.Room
import com.zhangzhu95.room.MoviesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Provides
    fun provideDatabase(@ApplicationContext context: Context): MoviesDatabase {
        return Room.databaseBuilder(
            context,
            MoviesDatabase::class.java, "movies-database"
        ).build()
    }

    @Provides
    fun provideMoviesHistoryDao(database: MoviesDatabase) = database.movieHistoryDao()

    @Provides
    fun provideSearchQueryDao(database: MoviesDatabase) = database.searchQueryDao()
}
