package com.zhangzhu95.details.ui

import com.zhangzhu95.core.ui.ViewState
import com.zhangzhu95.data.movies.models.Actor
import com.zhangzhu95.data.movies.models.MovieDetails

sealed class DetailsViewState : ViewState {

    object Loading : DetailsViewState()

    object Idle : DetailsViewState()

    data class Success(val details: MovieDetails, val actors: List<Actor>) : DetailsViewState()

    data class Error(val error: String) : DetailsViewState()

    object InvalidMovieId : DetailsViewState()
}
