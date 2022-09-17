package com.zhangzhu.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhangzhu95.core.networking.Response
import com.zhangzhu95.domain.movies.SearchMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchMoviesUseCase: SearchMoviesUseCase
) : ViewModel() {

    val viewState = MutableStateFlow<SearchViewState>(SearchViewState.Start)
    var query = MutableStateFlow("")

    fun search() {
        viewModelScope.launch {
            viewState.value = SearchViewState.Loading
            val newState = when (val response = searchMoviesUseCase(query.value)) {
                is Response.Success -> {
                    val list = response.data?.results.orEmpty()
                    if (list.isNotEmpty()) SearchViewState.SearchList(list) else SearchViewState.Empty
                }

                is Response.Error -> SearchViewState.Error(response.message)
            }
            viewState.value = newState
        }
    }

    fun onSearchQueryChanged(newQuery: String) {
        query.value = newQuery
    }
}
