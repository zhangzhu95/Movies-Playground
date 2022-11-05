package com.zhangzhu.search

import com.zhangzhu95.core.ui.ViewState
import com.zhangzhu95.data.movies.models.Movie
import com.zhangzhu95.data.movies.models.MovieHistory

sealed interface SearchViewState : ViewState {
    object Idle : SearchViewState
    object Empty : SearchViewState
    object Start : SearchViewState
    data class AutocompleteHistory(
        val recentlyViewed: List<MovieHistory>,
        val queryHistory: List<String>
    ) : SearchViewState

    data class SearchList(val list: List<Movie>) : SearchViewState
}
