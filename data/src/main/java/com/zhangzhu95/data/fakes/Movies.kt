package com.zhangzhu95.data.fakes

import com.zhangzhu95.data.movies.models.Movie
import com.zhangzhu95.data.movies.models.MovieHistory

val fakeMovies = listOf(
    Movie(id = 1, title = "Movie 1"),
    Movie(id = 2, title = "Movie 2"),
    Movie(id = 3, title = "Movie 3"),
)

val fakeMoviesHistory = listOf(
    MovieHistory(id = 0, "", "Movie 1"),
    MovieHistory(id = 1, "", "Movie 2"),
    MovieHistory(id = 3, "", "Movie 3"),
)
