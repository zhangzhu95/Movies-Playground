package com.zhangzhu95.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zhangzhu95.room.entities.MovieHistory

@Dao
interface MovieHistoryDao {

    @Query("SELECT * FROM moviehistory order by date_created desc")
    suspend fun getAll(): List<MovieHistory>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg movie: MovieHistory)

    @Delete
    suspend fun delete(movie: MovieHistory)
}
