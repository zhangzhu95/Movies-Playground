package com.zhangzhu95.trending.ui

import com.zhangzhu95.core.ui.ViewState
import com.zhangzhu95.domain.movies.models.HomeSections

internal sealed interface HomeViewState : ViewState {
    object Idle : HomeViewState
    object Loading : HomeViewState
    data class Sections(val sections: HomeSections) : HomeViewState
    data class Error(val message: String) : HomeViewState
}