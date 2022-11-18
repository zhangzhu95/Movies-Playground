package com.zhangzhu95.data.fakes

import com.zhangzhu95.data.movies.models.Movie
import com.zhangzhu95.data.movies.models.MovieHistory

val fakeMovies = (1..10).map {
    Movie(id = it, title = "Movie $it")
}

val fakeMovies2 = (20..30).map {
    Movie(id = it, title = "Movie $it")
}

val fakeMovies3 = (40..50).map {
    Movie(id = it, title = "Movie $it")
}

val fakeMoviesHistory = listOf(
    MovieHistory(id = 0, "", "Movie 1"),
    MovieHistory(id = 1, "", "Movie 2"),
    MovieHistory(id = 3, "", "Movie 3"),
)
