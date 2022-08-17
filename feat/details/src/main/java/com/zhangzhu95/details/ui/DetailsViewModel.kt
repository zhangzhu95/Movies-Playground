package com.zhangzhu95.details.ui

import androidx.lifecycle.SavedStateHandle
import com.zhangzhu95.core.ui.StateViewModel
import com.zhangzhu95.details.domain.FetchDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
internal class DetailsViewModel @Inject constructor(
    private val fetchDetailsUseCase: FetchDetailsUseCase,
    savedStateHandle: SavedStateHandle
) : StateViewModel<DetailsViewState>(DetailsViewState.Idle) {

    private val movieId = savedStateHandle.get<String>("movieId")

    override val viewState: Flow<DetailsViewState> = flow {
        emit(DetailsViewState.Loading)
        val result = fetchDetailsUseCase(movieId)
        emit(result)
    }
}
