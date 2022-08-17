package com.zhangzhu95.details.ui

import com.zhangzhu95.core.data.networking.details.MovieDetails
import com.zhangzhu95.core.ui.ViewState

sealed class DetailsViewState : ViewState {

    object Loading : DetailsViewState()

    object Idle : DetailsViewState()

    data class Success(val data: MovieDetails) : DetailsViewState()

    data class Error(val error: String) : DetailsViewState()

    object InvalidMovieId : DetailsViewState()
}
