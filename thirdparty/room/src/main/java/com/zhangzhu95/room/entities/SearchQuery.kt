package com.zhangzhu95.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class SearchQuery(
    @PrimaryKey val query: String,
    @ColumnInfo(name = "date_created") val dateCreated: Long = Calendar.getInstance().timeInMillis
)
