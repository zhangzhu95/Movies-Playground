package com.zhangzhu95.data.movies.models

data class MoviesListResponse(
    val page: Int,
    val results: List<Movie>
)
