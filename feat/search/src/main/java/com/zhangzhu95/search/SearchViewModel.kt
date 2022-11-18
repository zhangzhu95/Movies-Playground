package com.zhangzhu95.search

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhangzhu95.core.networking.Response
import com.zhangzhu95.domain.movies.GetPreviousSearchUseCase
import com.zhangzhu95.domain.movies.SearchMoviesUseCase
import com.zhangzhu95.domain.movies.models.PreviousSearch
import com.zhangzhu95.domain.search.SaveSearchQueryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchMoviesUseCase: SearchMoviesUseCase,
    private val saveSearchQueryUseCase: SaveSearchQueryUseCase,
    private val getPreviousSearchUseCase: GetPreviousSearchUseCase
) : ViewModel(), SearchPaginationActions {

    private val _viewState = MutableStateFlow<SearchViewState>(SearchViewState.Start)
    val viewState: StateFlow<SearchViewState> = _viewState.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        SearchViewState.Start
    )

    private val _events = MutableSharedFlow<SearchViewEvent>()
    val events = _events.shareIn(
        viewModelScope,
        SharingStarted.Eagerly
    )

    var query = MutableStateFlow("")

    override var job: Job? = null
    private val pagination = SearchPagination(this)

    init {
        loadPreviousSearchState()
    }

    @VisibleForTesting
    fun loadPreviousSearchState() {
        viewModelScope.launch {
            getPreviousSearchUseCase.invoke().map {
                _viewState.value = it.toViewState()
            }.collect()
        }
    }

    @VisibleForTesting
    fun PreviousSearch.toViewState(): SearchViewState {
        return if (keywords.isNotEmpty() || history.isNotEmpty()) {
            SearchViewState.AutocompleteHistory(history, keywords)
        } else {
            SearchViewState.Start
        }
    }

    override fun loadMovies(page: Int) {
        job = viewModelScope.launch {
            _events.emit(SearchViewEvent.Loading)

            when (val response = searchMoviesUseCase(query.value, page)) {
                is Response.Success -> {
                    pagination.getSuccessState(
                        _viewState.value,
                        response.data?.results.orEmpty()
                    )?.let {
                        _viewState.value = it
                    }
                    _events.emit(SearchViewEvent.Idle)
                }
                is Response.Error -> {
                    _viewState.value = SearchViewState.Idle
                    _events.emit(SearchViewEvent.Error(response.message))
                }
                else -> {}
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
            _events.emit(SearchViewEvent.NavigationMovieDetails(id))
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
