package com.zhangzhu95.testing.fake

import com.zhangzhu95.data.movies.models.MovieGenres

object FakeMovieGenres {

    val listMovieGenres = (1..3).map {
        MovieGenres(id = it, name = "category $it")
    }

}