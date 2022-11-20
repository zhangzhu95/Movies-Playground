package com.zhangzhu95.domain.movies

import com.zhangzhu95.core.networking.Response
import com.zhangzhu95.data.movies.MoviesRepository
import com.zhangzhu95.domain.movies.models.HomeSections
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FetchHomeSectionsUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) {

    operator fun invoke() = flow<Response<HomeSections>> {
        emit(Response.Loading)
        coroutineScope {
            val defTrending = async { moviesRepository.fetchTrendingAll() }
            val defUpcoming = async { moviesRepository.fetchUpcoming() }
            val defTopRated = async { moviesRepository.fetchTopRated() }

            val responseTrending = defTrending.await()
            val responseUpcoming = defUpcoming.await()
            val responseTopRated = defTopRated.await()

            val homeSections = HomeSections(
                trending = ((responseTrending as? Response.Success)?.data?.results ?: emptyList()),
                upcoming = ((responseUpcoming as? Response.Success)?.data?.results ?: emptyList()),
                topRated = ((responseTopRated as? Response.Success)?.data?.results ?: emptyList())
            )

            if (homeSections.trending.isEmpty() &&
                homeSections.topRated.isEmpty() &&
                homeSections.upcoming.isEmpty()
            ) {
                emit(Response.Error("Empty"))
            }else{
                emit(Response.Success(homeSections))
            }
        }
    }

}