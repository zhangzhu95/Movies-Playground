package com.zhangzhu95.domain.movies

import com.zhangzhu95.data.movies.MoviesRepository
import com.zhangzhu95.data.movies.models.MovieHistory
import javax.inject.Inject

class GetMoviesInHistory @Inject constructor(
    private val moviesRepository: MoviesRepository
) {
    suspend operator fun invoke(): List<MovieHistory> {
        return moviesRepository.getMoviesHistory()
    }
}
