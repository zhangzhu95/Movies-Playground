package com.zhangzhu95.data.fakes

import com.zhangzhu95.data.movies.models.Movie
import com.zhangzhu95.data.movies.models.MovieHistory

object FakeMovies {

    val fakeMovies1 = (1..10).map {
        Movie(id = it, title = "Movie $it")
    }

    val fakeMovies2 = (20..30).map {
        Movie(id = it, title = "Movie $it")
    }

    val fakeMovies3 = (40..50).map {
        Movie(id = it, title = "Movie $it")
    }

    val fakeMoviesHistory = (1..3).map {
        MovieHistory(id = it, "Poster $it", "Movie $it")
    }
}
