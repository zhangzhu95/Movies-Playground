package com.zhangzhu95.domain.movies.models

import com.zhangzhu95.data.movies.models.MovieHistory

data class PreviousSearch(
    val history: List<MovieHistory>,
    val keywords: List<String>
)