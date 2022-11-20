package com.zhangzhu95.domain.movies

import com.zhangzhu95.core.networking.Response
import com.zhangzhu95.data.movies.MoviesRepository
import com.zhangzhu95.data.movies.models.MoviesListResponse
import javax.inject.Inject

class FetchUpcomingMoviesUseCase @Inject constructor(private val moviesRepository: MoviesRepository) {

    suspend operator fun invoke(): Response<MoviesListResponse> {
        return moviesRepository.fetchUpcoming()
    }
}
