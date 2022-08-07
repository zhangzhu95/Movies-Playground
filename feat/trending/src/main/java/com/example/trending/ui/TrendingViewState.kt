package com.example.trending.ui

import com.example.core.data.networking.trending.Movie
import com.example.core.ui.ViewState

internal sealed class TrendingViewState : ViewState {
    object Idle : TrendingViewState()
    object Loading : TrendingViewState()
    data class Success(val list: List<Movie>) : TrendingViewState()
    data class Error(val message: String) : TrendingViewState()
}