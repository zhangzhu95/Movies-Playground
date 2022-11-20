package com.zhangzhu95.domain.movies

import com.zhangzhu95.core.networking.Response
import com.zhangzhu95.data.actors.ActorsRepository
import com.zhangzhu95.data.movies.MoviesRepository
import com.zhangzhu95.domain.movies.models.MovieFullDetails
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FetchMovieDetailsUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository,
    private val actorsRepository: ActorsRepository
) {
    operator fun invoke(id: String?) = flow {
        if (id.orEmpty().isNotEmpty()) {
            val details = moviesRepository.fetchDetails(id!!)
            emit(Response.Loading)

            when (details) {
                is Response.Success -> {
                    val responseCast = actorsRepository.getCredits(id)
                    val cast = (responseCast as? Response.Success)?.data
                    emit(
                        Response.Success(
                            MovieFullDetails(
                                details = details.data!!,
                                cast = cast
                            )
                        )
                    )
                }
                else -> emit(Response.Error(message = "Failed to load the screen"))
            }
        } else {
            emit(Response.Error("Error"))
        }
    }
}