package com.zhangzhu95.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zhangzhu95.room.dao.MovieHistoryDao
import com.zhangzhu95.room.entities.MovieHistory

@Database(entities = [MovieHistory::class], version = 1)
abstract class MoviesDatabase : RoomDatabase() {
    abstract fun movieHistoryDao(): MovieHistoryDao
}
