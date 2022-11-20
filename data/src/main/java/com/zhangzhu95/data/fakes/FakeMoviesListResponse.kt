package com.zhangzhu95.data.fakes

import com.zhangzhu95.data.fakes.FakeMovies.fakeMovies1
import com.zhangzhu95.data.fakes.FakeMovies.fakeMovies2
import com.zhangzhu95.data.fakes.FakeMovies.fakeMovies3
import com.zhangzhu95.data.movies.models.MoviesListResponse

object FakeMoviesListResponse {
    val regular1 = MoviesListResponse(
        page = 10,
        results = fakeMovies1
    )

    val regular2 = MoviesListResponse(
        page = 10,
        results = fakeMovies2
    )

    val regular3 = MoviesListResponse(
        page = 10,
        results = fakeMovies3
    )
}