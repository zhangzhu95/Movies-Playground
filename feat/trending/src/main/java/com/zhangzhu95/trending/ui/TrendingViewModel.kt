package com.zhangzhu95.trending.ui

import androidx.lifecycle.viewModelScope
import com.zhangzhu95.core.ui.StateViewModel
import com.zhangzhu95.trending.domain.FetchTrendingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class TrendingViewModel @Inject constructor(
    private val fetchTrendingViewState: FetchTrendingUseCase
) : StateViewModel<TrendingViewState>(TrendingViewState.Idle) {

    override val viewState = MutableStateFlow<TrendingViewState>(TrendingViewState.Idle)

    init {
        loadMovies()
    }

    private fun loadMovies() {
        viewModelScope.launch {
            viewState.value = TrendingViewState.Loading
            val result = fetchTrendingViewState()
            viewState.value = result
        }
    }

}
