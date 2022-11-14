package com.zhangzhu95.testing.fake

import com.zhangzhu95.data.movies.models.MovieDetails

object FakeMovieDetails {

    val regularDetails = MovieDetails(
        id = 10,
        imdbId = "156546",
        backdropPath = "image.png",
        genres = FakeMovieGenres.listMovieGenres,
        releaseDate = "10/10/2018",
        homepage = "http://google.com",
        runtime = 150,
        title = "Endgame",
        voteCount = 10,
        voteAverage = 10.0,
        posterPath = "image.png",
        overview = "Overview",
        originalTitle = "Title"
    )

}