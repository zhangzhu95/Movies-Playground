package com.zhangzhu95.domain.movies.models

import com.zhangzhu95.data.movies.models.Movie

data class HomeSections(
    val trending: List<Movie> = emptyList(),
    val topRated: List<Movie> = emptyList(),
    val upcoming: List<Movie> = emptyList()
)