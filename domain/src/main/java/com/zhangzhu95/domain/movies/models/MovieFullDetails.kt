package com.zhangzhu95.domain.movies.models

import com.zhangzhu95.data.movies.models.CastResponse
import com.zhangzhu95.data.movies.models.MovieDetails

data class MovieFullDetails(
    val details: MovieDetails,
    val cast: CastResponse?
)