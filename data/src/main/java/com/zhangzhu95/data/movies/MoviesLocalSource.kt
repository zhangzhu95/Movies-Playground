package com.zhangzhu95.data.movies

import com.zhangzhu95.data.movies.models.MovieHistory

interface MoviesLocalSource {

    suspend fun addToHistory(movie: MovieHistory)

    suspend fun getMoviesHistory(): List<MovieHistory>
}
