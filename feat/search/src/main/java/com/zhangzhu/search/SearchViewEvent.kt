package com.zhangzhu.search

sealed interface SearchViewEvent {
    object Idle : SearchViewEvent
    object Loading : SearchViewEvent
    data class Error(val message: String) : SearchViewEvent
}
