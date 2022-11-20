package com.zhangzhu95.search

import com.zhangzhu95.data.movies.models.Movie
import kotlinx.coroutines.Job

internal class SearchPagination(private val actions: SearchPaginationActions) {

    private var currentPage = 1
    private var stopLoadingMore = false
    private var isFirstSearch = true

    internal fun newSearch() {
        reset()
        actions.loadMovies(currentPage)
    }

    internal fun loadMore() {
        if (actions.job?.isCompleted != false && !stopLoadingMore) {
            isFirstSearch = false
            actions.loadMovies(currentPage + 1)
        }
    }

    internal fun getSuccessState(
        previousViewState: SearchViewState,
        list: List<Movie>
    ): SearchViewState? {
        return if (isFirstSearch) {
            // Successful first search
            if (list.isNotEmpty()) {
                SearchViewState.SearchList(list)
            } else {
                SearchViewState.Empty
            }
        } else {
            // Successful movies loading
            if (list.isNotEmpty()) {
                currentPage++
                val previousList =
                    (previousViewState as? SearchViewState.SearchList)?.list.orEmpty()
                SearchViewState.SearchList(previousList + list)
            } else {
                stopLoadingMore = true
                null
            }
        }
    }

    private fun reset() {
        currentPage = 1
        stopLoadingMore = false
        isFirstSearch = true
        actions.job?.cancel()
    }
}

interface SearchPaginationActions {
    var job: Job?
    fun loadMovies(page: Int)
}
