package com.zhangzhu95.room.extensions

import com.zhangzhu95.data.movies.models.MovieHistory

fun MovieHistory.toDb() = com.zhangzhu95.room.entities.MovieHistory(
    id,
    poster,
    name
)

fun com.zhangzhu95.room.entities.MovieHistory.toModel() = MovieHistory(
    id,
    poster,
    name
)
