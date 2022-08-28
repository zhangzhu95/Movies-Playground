package com.zhangzhu95.data.movies.models

import com.google.gson.annotations.SerializedName

data class Movie(
    val adult: Boolean = false,
    @SerializedName("backdrop_path")
    val backdropPath: String = "",
    val id: Int = 0,
    @SerializedName(value = "title", alternate = ["name"])
    val title: String = "",
    @SerializedName("original_language")
    val originalLanguage: String = "",
    @SerializedName("original_title")
    val originalTitle: String = "",
    val overview: String = "",
    @SerializedName("poster_path")
    val posterPath: String = "",
    @SerializedName("media_type")
    val mediaType: String = "",
    val popularity: Double = 0.0,
    @SerializedName("release_date")
    val releaseDate: String = "",
    val video: Boolean = false,
    @SerializedName("vote_count")
    val voteCount: Int = 0,
    @SerializedName("vote_average")
    val voteAverage: Double = 0.0
)
