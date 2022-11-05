package com.zhangzhu95.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zhangzhu95.room.entities.SearchQuery

@Dao
interface SearchQueryDao {

    @Query("SELECT * FROM searchquery LIMIT 10")
    suspend fun getSearchQueries(): List<SearchQuery>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg query: SearchQuery)

    @Delete
    suspend fun delete(searchQuery: SearchQuery)
}
