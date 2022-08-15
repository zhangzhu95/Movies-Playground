package com.zhangzhu95.trending.ui

import com.zhangzhu95.core.data.networking.trending.Movie
import com.zhangzhu95.core.ui.ViewState

internal sealed class TrendingViewState : ViewState {
    object Idle : TrendingViewState()
    object Loading : TrendingViewState()
    data class Success(val list: List<Movie>) : TrendingViewState()
    data class Error(val message: String) : TrendingViewState()
}