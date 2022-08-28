package com.zhangzhu95.details.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.zhangzhu95.core.ui.StateViewModel
import com.zhangzhu95.data.networking.Response
import com.zhangzhu95.domain.movies.FetchDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class DetailsViewModel @Inject constructor(
    private val fetchDetailsUseCase: FetchDetailsUseCase,
    savedStateHandle: SavedStateHandle
) : StateViewModel<DetailsViewState>(DetailsViewState.Idle) {

    private val movieId = savedStateHandle.get<String>("movieId")

    override val viewState = MutableStateFlow<DetailsViewState>(DetailsViewState.Idle)

    init {
        loadDetails()
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
                is Response.Success -> DetailsViewState.Success(result.data!!)
                is Response.Error -> DetailsViewState.Error(result.message)
            }
        }
    }

}
