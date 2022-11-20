package com.zhangzhu95.trending.ui

import com.zhangzhu95.core.ui.ViewState
import com.zhangzhu95.data.movies.models.Movie

internal sealed interface HomeViewState : ViewState {
    object Idle : HomeViewState
    object Loading : HomeViewState
    data class Success(val list: List<HomeSections>) : HomeViewState
    data class Error(val message: String) : HomeViewState
}

internal sealed class HomeSections {
    sealed class HorizontalMoviesSection(open var list: List<Movie>) : HomeSections(){
        data class TrendingMoviesSection(override var list: List<Movie>): HorizontalMoviesSection(list)
        data class TopRatedMoviesSection(override var list: List<Movie>): HorizontalMoviesSection(list)
        data class UpcomingMoviesSection(override var list: List<Movie>): HorizontalMoviesSection(list)
    }
}
