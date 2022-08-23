package com.zhangzhu95.core.data.networking.details

data class MovieDetails(
    val backdrop_path: String = "",
    val genres: List<MovieGenres> = emptyList(),
    val homepage: String = "",
    val id: Int = 0,
    val imdb_id: String = "",
    val original_language: String = "",
    val original_title: String = "",
    val title: String = "",
    val overview: String = "",
    val poster_path: String = "",
    val tagline: String = "",
    val release_date: String = "",
    val vote_average: Double = 0.0,
    val vote_count: Int = 0,
    val runtime: Int = 0
)

data class MovieGenres(
    val id: Int = 0,
    val name: String = ""
)
