package com.zhangzhu95.room.data

import com.zhangzhu95.data.movies.MoviesLocalSource
import com.zhangzhu95.data.movies.models.MovieHistory
import com.zhangzhu95.room.dao.MovieHistoryDao
import com.zhangzhu95.room.extensions.toDb
import com.zhangzhu95.room.extensions.toModel
import javax.inject.Inject

class MoviesLocalSourceImpl @Inject constructor(
    private val movieHistoryDao: MovieHistoryDao
) : MoviesLocalSource {

    override suspend fun addToHistory(movie: MovieHistory) {
        movieHistoryDao.insertAll(movie.toDb())
    }

    override suspend fun getMoviesHistory(): List<MovieHistory> {
        return movieHistoryDao.getAll().map { it.toModel() }
    }
}
