package com.zhangzhu95.trending.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhangzhu95.data.networking.Response
import com.zhangzhu95.domain.movies.FetchTopRatedMoviesUseCase
import com.zhangzhu95.domain.movies.FetchTrendingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    private val fetchTrendingViewState: FetchTrendingUseCase,
    private val fetchTopRatedMoviesUseCase: FetchTopRatedMoviesUseCase,
) : ViewModel() {

    val trendingViewState = MutableStateFlow<HomeViewState>(HomeViewState.Idle)
    val topRatedViewState = MutableStateFlow<HomeViewState>(HomeViewState.Idle)

    init {
        loadTrending()
        loadTopRated()
    }

    private fun loadTrending() {
        viewModelScope.launch {
            trendingViewState.value = HomeViewState.Loading
            val result = fetchTrendingViewState()
            trendingViewState.value = when (result) {
                is Response.Success -> HomeViewState.Success(result.data?.results ?: emptyList())
                is Response.Error -> HomeViewState.Error(result.message)
            }
        }
    }

    private fun loadTopRated() {
        viewModelScope.launch {
            topRatedViewState.value = HomeViewState.Loading
            val result = fetchTopRatedMoviesUseCase()
            topRatedViewState.value = when (result) {
                is Response.Success -> HomeViewState.Success(result.data?.results ?: emptyList())
                is Response.Error -> HomeViewState.Error(result.message)
            }
        }
    }
}
