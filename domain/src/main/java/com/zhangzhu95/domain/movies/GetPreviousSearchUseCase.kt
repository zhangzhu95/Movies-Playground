package com.zhangzhu95.domain.movies

import com.zhangzhu95.data.movies.MoviesRepository
import com.zhangzhu95.data.search.SearchRepository
import com.zhangzhu95.domain.movies.models.PreviousSearch
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetPreviousSearchUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository,
    private val searchRepository: SearchRepository
) {

    operator fun invoke() = flow {
        coroutineScope {
            val keywordsCall = async { searchRepository.getQueries() }
            val historyCall = async { moviesRepository.getMoviesHistory() }

            val keywords = keywordsCall.await()
            val history = historyCall.await()

            emit(
                PreviousSearch(
                    keywords = keywords,
                    history = history
                )
            )
        }
    }
}