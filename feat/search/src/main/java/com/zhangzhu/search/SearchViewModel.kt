package com.zhangzhu.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhangzhu95.core.networking.Response
import com.zhangzhu95.domain.movies.GetMoviesInHistory
import com.zhangzhu95.domain.movies.SearchMoviesUseCase
import com.zhangzhu95.domain.search.GetSearchQueriesUseCase
import com.zhangzhu95.domain.search.SaveSearchQueryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchMoviesUseCase: SearchMoviesUseCase,
    private val getMoviesInHistory: GetMoviesInHistory,
    private val saveSearchQueryUseCase: SaveSearchQueryUseCase,
    private val getSearchQueriesUseCase: GetSearchQueriesUseCase
) : ViewModel(), SearchPaginationActions {

    val viewState = MutableStateFlow<SearchViewState>(SearchViewState.Start)
    val events = MutableSharedFlow<SearchViewEvent>()
    var query = MutableStateFlow("")

    override var job: Job? = null
    private val pagination = SearchPagination(this)

    init {
        loadSearchHistory()
    }

    override fun loadMovies(page: Int) {
        job = viewModelScope.launch {
            events.emit(SearchViewEvent.Loading)

            when (val response = searchMoviesUseCase(query.value, page)) {
                is Response.Success -> {
                    pagination.getSuccessState(
                        viewState.value,
                        response.data?.results.orEmpty()
                    )?.let {
                        viewState.value = it
                    }
                    events.emit(SearchViewEvent.Idle)
                }
                is Response.Error -> {
                    viewState.value = SearchViewState.Idle
                    events.emit(SearchViewEvent.Error(response.message))
                }
            }
        }
    }

    private fun loadSearchHistory() {
        viewModelScope.launch {
            val queriesHistory = getSearchQueriesUseCase()
            val recentlyViewedHistory = getMoviesInHistory()

            if (queriesHistory.isNotEmpty() || recentlyViewedHistory.isNotEmpty()) {
                viewState.value = SearchViewState.AutocompleteHistory(
                    recentlyViewedHistory, queriesHistory
                )
            }
        }
    }

    private fun saveQueryLocally() {
        viewModelScope.launch {
            saveSearchQueryUseCase.invoke(query.value)
        }
    }

    fun onMovieClicked(id: Int) {
        viewModelScope.launch {
            events.emit(SearchViewEvent.NavigationMovieDetails(id))
        }
    }

    fun onNewSearch() {
        pagination.newSearch()
        saveQueryLocally()
    }

    fun onLoadMore() {
        pagination.loadMore()
    }

    fun onSearchQueryChanged(newQuery: String) {
        if (query.value == newQuery) {
            return
        }

        query.value = newQuery
    }
}
