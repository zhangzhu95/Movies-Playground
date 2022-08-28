package com.zhangzhu95.domain.movies

import com.zhangzhu95.data.movies.MoviesRepository
import com.zhangzhu95.data.movies.models.MovieDetails
import com.zhangzhu95.data.networking.Response
import javax.inject.Inject

class FetchDetailsUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) {

    suspend operator fun invoke(id: String): Response<MovieDetails> {
        return moviesRepository.fetchDetails(id)
    }
}
