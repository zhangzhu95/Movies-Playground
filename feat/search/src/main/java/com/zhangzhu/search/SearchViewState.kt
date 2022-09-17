package com.zhangzhu.search

import com.zhangzhu95.core.ui.ViewState
import com.zhangzhu95.data.movies.models.Movie

sealed interface SearchViewState : ViewState {
    object Idle : SearchViewState
    object Empty : SearchViewState
    object Start : SearchViewState
    object Loading : SearchViewState
    data class SearchList(val list: List<Movie>) : SearchViewState
    data class Error(val message: String) : SearchViewState
}
