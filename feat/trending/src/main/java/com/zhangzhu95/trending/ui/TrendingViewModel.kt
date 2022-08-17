package com.zhangzhu95.trending.ui

import com.zhangzhu95.core.ui.StateViewModel
import com.zhangzhu95.trending.domain.FetchTrendingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
internal class TrendingViewModel @Inject constructor(
    private val fetchTrendingViewState: FetchTrendingUseCase
) : StateViewModel<TrendingViewState>(TrendingViewState.Idle) {

    override val viewState: Flow<TrendingViewState> = flow {
        emit(TrendingViewState.Loading)
        val result = fetchTrendingViewState()
        emit(result)
    }
}
