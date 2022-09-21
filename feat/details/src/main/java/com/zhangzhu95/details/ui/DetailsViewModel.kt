package com.zhangzhu95.details.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhangzhu95.core.networking.Response
import com.zhangzhu95.data.movies.models.CastResponse
import com.zhangzhu95.data.movies.models.MovieDetails
import com.zhangzhu95.data.movies.models.MovieHistory
import com.zhangzhu95.domain.actors.FetchMovieActorsUseCase
import com.zhangzhu95.domain.movies.FetchDetailsUseCase
import com.zhangzhu95.domain.movies.SaveMovieInHistoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class DetailsViewModel @Inject constructor(
    private val fetchDetailsUseCase: FetchDetailsUseCase,
    private val fetchMovieActorsUseCase: FetchMovieActorsUseCase,
    private val saveMovieInHistoryUseCase: SaveMovieInHistoryUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val viewState = MutableStateFlow<DetailsViewState>(DetailsViewState.Idle)
    private val movieId = savedStateHandle.get<String>("movieId")

    init {
        loadDetails()
    }

    private fun loadDetails() = viewModelScope.launch(Dispatchers.IO) {
        if (movieId.isNullOrEmpty()) {
            return@launch
        }

        viewState.value = DetailsViewState.Loading

        val detailsCall = async { fetchDetailsUseCase(movieId) }
        val actorsCall = async { fetchMovieActorsUseCase(movieId) }

        val detailsResponse = detailsCall.await()
        val actorsResponse = actorsCall.await()

        when (detailsResponse) {
            is Response.Success -> handleSuccessResponse(detailsResponse, actorsResponse)
            is Response.Error -> viewState.value = DetailsViewState.Error(detailsResponse.message)
        }
    }

    private suspend fun handleSuccessResponse(
        detailsResponse: Response.Success<MovieDetails>,
        actorsResponse: Response<CastResponse>
    ) {
        val movie = detailsResponse.data!!
        val actors = (actorsResponse as? Response.Success)?.data?.cast ?: emptyList()

        viewState.value = DetailsViewState.Success(movie, actors)

        saveMovieInHistoryUseCase.invoke(
            MovieHistory(id = movie.id, name = movie.originalTitle, poster = movie.posterPath)
        )
    }
}
