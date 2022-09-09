package com.zhangzhu95.trending.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhangzhu95.data.networking.Response
import com.zhangzhu95.domain.movies.FetchTopRatedMoviesUseCase
import com.zhangzhu95.domain.movies.FetchTrendingUseCase
import com.zhangzhu95.trending.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    private val fetchTrendingViewState: FetchTrendingUseCase,
    private val fetchTopRatedMoviesUseCase: FetchTopRatedMoviesUseCase,
) : ViewModel() {

    val viewState = MutableStateFlow<HomeViewState>(HomeViewState.Idle)

    init {
        loadTrending()
        loadTopRated()
    }

    private fun loadTrending() {
        viewModelScope.launch {
            viewState.value = HomeViewState.Loading
            val result = fetchTrendingViewState()
            viewState.value = when (result) {
                is Response.Success -> getSectionsUpdatedState(
                    HomeSections.HorizontalMoviesSection(
                        R.string.trending,
                        result.data?.results.orEmpty()
                    )
                )
                is Response.Error -> HomeViewState.Error(result.message)
            }
        }
    }

    private fun loadTopRated() {
        viewModelScope.launch {
            viewState.value = HomeViewState.Loading
            val result = fetchTopRatedMoviesUseCase()
            viewState.value = when (result) {
                is Response.Success -> getSectionsUpdatedState(
                    HomeSections.HorizontalMoviesSection(
                        R.string.top_rated,
                        result.data?.results.orEmpty()
                    )
                )
                is Response.Error -> HomeViewState.Error(result.message)
            }
        }
    }

    private fun getSectionsUpdatedState(section: HomeSections): HomeViewState.Success {
        return (viewState.value as? HomeViewState.Success)?.let {
            it.copy(list = it.list + section)
        } ?: run {
            HomeViewState.Success(listOf(section))
        }
    }
}
