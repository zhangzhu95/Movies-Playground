package com.zhangzhu95.core.data.networking.trending

import com.google.gson.annotations.SerializedName

data class Movie(
    val adult: Boolean = false,
    val backdrop_path: String = "",
    val id: Int = 0,
    @SerializedName(value = "title", alternate = ["name"])
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
)
