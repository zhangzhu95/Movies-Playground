package com.zhangzhu95.data.movies.models

import com.google.gson.annotations.SerializedName
import com.zhangzhu95.core.helpers.extensions.round

data class MovieDetails(
    @SerializedName("backdrop_path")
    val backdropPath: String? = "",
    val genres: List<MovieGenres> = emptyList(),
    val homepage: String = "",
    val id: Int = 0,
    @SerializedName("imdb_id")
    val imdbId: String = "",
    @SerializedName("original_language")
    val originalLanguage: String = "",
    @SerializedName("original_title")
    val originalTitle: String = "",
    val title: String = "",
    val overview: String = "",
    @SerializedName("poster_path")
    val posterPath: String = "",
    val tagline: String = "",
    @SerializedName("release_date")
    val releaseDate: String = "",
    @SerializedName("vote_average")
    val voteAverage: Double = 0.0,
    @SerializedName("vote_count")
    val voteCount: Int = 0,
    val runtime: Int = 0
) {
    val rating by lazy {
        voteAverage.round(1)
    }
}

data class MovieGenres(
    val id: Int = 0,
    val name: String = ""
)
