package com.zhangzhu95.search

sealed interface SearchViewEvent {
    object Idle : SearchViewEvent
    object Loading : SearchViewEvent
    data class Error(val message: String) : SearchViewEvent
    data class NavigationMovieDetails(val id: Int) : SearchViewEvent
}
