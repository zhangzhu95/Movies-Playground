package com.zhangzhu95.details.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhangzhu95.core.networking.Response
import com.zhangzhu95.domain.actors.FetchMovieActorsUseCase
import com.zhangzhu95.domain.movies.FetchDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class DetailsViewModel @Inject constructor(
    private val fetchDetailsUseCase: FetchDetailsUseCase,
    private val fetchMovieActorsUseCase: FetchMovieActorsUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val viewState = MutableStateFlow<DetailsViewState>(DetailsViewState.Idle)
    private val movieId = savedStateHandle.get<String>("movieId")

    init {
        loadDetails()
    }

    private fun loadDetails() = viewModelScope.launch {
        if (movieId.isNullOrEmpty()) {
            return@launch
        }

        viewState.value = DetailsViewState.Loading

        val detailsCall = async { fetchDetailsUseCase(movieId) }
        val actorsCall = async { fetchMovieActorsUseCase(movieId) }

        val details = detailsCall.await()
        val actors = actorsCall.await()

        viewState.value = when (details) {
            is Response.Success -> DetailsViewState.Success(
                details.data!!, (actors as? Response.Success)?.data?.cast ?: emptyList()
            )
            is Response.Error -> DetailsViewState.Error(details.message)
        }
    }
}
