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
    fun getAll(): List<MovieHistory>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg movie: MovieHistory)

    @Delete
    fun delete(movie: MovieHistory)
}
