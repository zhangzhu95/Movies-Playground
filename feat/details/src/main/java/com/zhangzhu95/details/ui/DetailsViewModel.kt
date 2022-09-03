package com.zhangzhu95.details.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.zhangzhu95.core.ui.StateViewModel
import com.zhangzhu95.data.networking.Response
import com.zhangzhu95.domain.actors.FetchMovieActorsUseCase
import com.zhangzhu95.domain.movies.FetchDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class DetailsViewModel @Inject constructor(
    private val fetchDetailsUseCase: FetchDetailsUseCase,
    private val fetchMovieActorsUseCase: FetchMovieActorsUseCase,
    savedStateHandle: SavedStateHandle
) : StateViewModel<DetailsViewState>(DetailsViewState.Idle) {

    private val movieId = savedStateHandle.get<String>("movieId")

    override val viewState = MutableStateFlow<DetailsViewState>(DetailsViewState.Idle)

    init {
        loadDetails()
        loadActors()
    }

    private fun loadActors() {
        viewModelScope.launch {
            if (movieId.isNullOrEmpty()) {
                return@launch
            }

            val result = fetchMovieActorsUseCase(movieId)
            viewState.value = when (result) {
                is Response.Success -> DetailsViewState.Actors(result.data!!.cast)
                is Response.Error -> DetailsViewState.Error(result.message)
            }
        }
    }

    private fun loadDetails() {
        viewModelScope.launch {

            if (movieId.isNullOrEmpty()) {
                viewState.value = DetailsViewState.InvalidMovieId
                return@launch
            }

            viewState.value = DetailsViewState.Loading
            val result = fetchDetailsUseCase(movieId)

            viewState.value = when (result) {
                is Response.Success -> DetailsViewState.Details(result.data!!)
                is Response.Error -> DetailsViewState.Error(result.message)
            }
        }
    }

}
