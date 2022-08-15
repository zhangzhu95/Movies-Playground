package com.zhangzhu95.core.data.networking.trending

import com.zhangzhu95.core.config.Config

data class Movie(
    val adult: Boolean = false,
    val backdrop_path: String = "",
    val id: Int = 0,
    val title: String = "",
    val original_language: String = "",
    val original_title: String = "",
    val overview: String = "",
    val poster_path: String = "",
    val media_type: String = "",
    val popularity: Double = 0.0,
    val release_date: String = "",
    val video: Boolean = false,
    val vote_count: Int = 0,
    val vote_average: Double = 0.0
) {
    val fullPoster: String
        get() = Config.IMAGES_PREFIX + poster_path
}