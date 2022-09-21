package com.zhangzhu95.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Calendar

@Entity
data class MovieHistory(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "poster") val poster: String?,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "date_created") val dateCreated: Long = Calendar.getInstance().timeInMillis
)
