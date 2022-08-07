package com.example.trending.domain

import com.example.core.data.networking.Response
import com.example.trending.data.TrendingRepository
import com.example.trending.ui.TrendingViewState
import javax.inject.Inject

internal class FetchTrendingUseCase @Inject constructor(
    private val trendingRepository: TrendingRepository
) {

    suspend operator fun invoke(): TrendingViewState {
        val result = trendingRepository.fetchTrendingAll()
        return when (result) {
            is Response.Success -> TrendingViewState.Success(result.data?.results ?: emptyList())
            is Response.Error -> TrendingViewState.Error(result.message)
        }
    }

}