package com.zhangzhu95.domain.actors

import com.zhangzhu95.data.actors.ActorsRepository
import com.zhangzhu95.data.movies.models.CastResponse
import com.zhangzhu95.data.networking.Response
import javax.inject.Inject

class FetchMovieActorsUseCase @Inject constructor(
    private val actorsRepository: ActorsRepository
) {

    suspend operator fun invoke(movieId: String): Response<CastResponse> =
        actorsRepository.getCredits(movieId)
}
