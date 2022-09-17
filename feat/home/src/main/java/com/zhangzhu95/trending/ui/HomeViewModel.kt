package com.zhangzhu95.trending.ui

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhangzhu95.core.networking.Response
import com.zhangzhu95.data.movies.models.MoviesListResponse
import com.zhangzhu95.domain.movies.FetchTopRatedMoviesUseCase
import com.zhangzhu95.domain.movies.FetchTrendingUseCase
import com.zhangzhu95.domain.movies.FetchUpcomingMoviesUseCase
import com.zhangzhu95.trending.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    private val fetchTrendingViewState: FetchTrendingUseCase,
    private val fetchTopRatedMoviesUseCase: FetchTopRatedMoviesUseCase,
    private val fetchUpcomingMoviesUseCase: FetchUpcomingMoviesUseCase,
) : ViewModel() {

    val viewState = MutableStateFlow<HomeViewState>(HomeViewState.Loading)

    init {
        loadTrending()
        loadTopRated()
        loadUpcoming()
    }

    private fun loadTrending() = viewModelScope.launch {
        val response = fetchTrendingViewState()
        handleMoviesSectionResponse(R.string.trending, response)
    }


    private fun loadTopRated() = viewModelScope.launch {
        val response = fetchTopRatedMoviesUseCase()
        handleMoviesSectionResponse(R.string.top_rated, response)
    }


    private fun loadUpcoming() = viewModelScope.launch {
        val response = fetchUpcomingMoviesUseCase()
        handleMoviesSectionResponse(R.string.upcoming, response)
    }

    private fun handleMoviesSectionResponse(
        @StringRes sectionTitle: Int, response: Response<MoviesListResponse>
    ) {
        viewState.value = when (response) {
            is Response.Success -> getSectionsUpdatedState(
                HomeSections.HorizontalMoviesSection(
                    sectionTitle, response.data?.results.orEmpty()
                )
            )
            is Response.Error -> HomeViewState.Error(response.message)
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
