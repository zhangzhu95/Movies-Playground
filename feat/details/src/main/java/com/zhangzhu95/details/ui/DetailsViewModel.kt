package com.zhangzhu95.details.ui

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhangzhu95.core.helpers.extensions.doOnSuccess
import com.zhangzhu95.core.networking.Response
import com.zhangzhu95.data.movies.models.MovieHistory
import com.zhangzhu95.domain.movies.FetchMovieDetailsUseCase
import com.zhangzhu95.domain.movies.SaveMovieInHistoryUseCase
import com.zhangzhu95.domain.movies.models.MovieFullDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
internal class DetailsViewModel @Inject constructor(
    fetchMovieDetailsUseCase: FetchMovieDetailsUseCase,
    private val saveMovieInHistoryUseCase: SaveMovieInHistoryUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val viewState: StateFlow<DetailsViewState> = fetchMovieDetailsUseCase.invoke(
        savedStateHandle["movieId"]
    ).doOnSuccess(this::saveMovieInHistory).map {
        it.toViewState()
    }.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        DetailsViewState.Loading
    )

    @VisibleForTesting
    suspend fun saveMovieInHistory(response: Response.Success<MovieFullDetails>) {
        val movie = response.data!!.details
        saveMovieInHistoryUseCase.invoke(
            MovieHistory(id = movie.id, name = movie.originalTitle, poster = movie.posterPath)
        )
    }

    @VisibleForTesting
    fun Response<MovieFullDetails>.toViewState() = when (this) {
        is Response.Success -> {
            val response = this.data!!
            DetailsViewState.Success(
                details = response.details,
                actors = response.cast?.cast ?: emptyList()
            )
        }
        is Response.Error -> DetailsViewState.Error(this.message)
        is Response.Loading -> DetailsViewState.Loading
    }
}
