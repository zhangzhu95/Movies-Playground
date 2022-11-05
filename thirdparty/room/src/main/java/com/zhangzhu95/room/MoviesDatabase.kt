package com.zhangzhu95.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zhangzhu95.room.dao.MovieHistoryDao
import com.zhangzhu95.room.dao.SearchQueryDao
import com.zhangzhu95.room.entities.MovieHistory
import com.zhangzhu95.room.entities.SearchQuery

@Database(entities = [MovieHistory::class, SearchQuery::class], version = 2)
abstract class MoviesDatabase : RoomDatabase() {
    abstract fun movieHistoryDao(): MovieHistoryDao
    abstract fun searchQueryDao(): SearchQueryDao
}
