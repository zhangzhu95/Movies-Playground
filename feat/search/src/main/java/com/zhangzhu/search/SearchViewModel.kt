package com.zhangzhu.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhangzhu95.core.networking.Response
import com.zhangzhu95.domain.movies.SearchMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchMoviesUseCase: SearchMoviesUseCase
) : ViewModel(), SearchPaginationActions {

    val viewState = MutableStateFlow<SearchViewState>(SearchViewState.Start)
    val events = MutableSharedFlow<SearchViewEvent>()
    var query = MutableStateFlow("")
    override var job: Job? = null
    private val pagination = SearchPagination(this)

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

    fun onNewSearch() {
        pagination.newSearch()
    }

    fun onLoadMore() {
        pagination.loadMore()
    }

    fun onSearchQueryChanged(newQuery: String) {
        query.value = newQuery
    }
}
