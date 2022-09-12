package com.zhangzhu95.trending.ui

import com.zhangzhu95.core.ui.ViewState
import com.zhangzhu95.data.movies.models.Movie

internal sealed class HomeViewState : ViewState {
    object Idle : HomeViewState()
    object Loading : HomeViewState()
    data class Success(val list: List<HomeSections>) : HomeViewState()
    data class Error(val message: String) : HomeViewState()
}

internal sealed class HomeSections constructor(val sectionTitle: Int) {
    data class HorizontalMoviesSection(val title: Int, val list: List<Movie>) :
        HomeSections(title)
}
