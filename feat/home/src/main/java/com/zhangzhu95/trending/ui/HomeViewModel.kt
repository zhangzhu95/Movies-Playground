package com.zhangzhu95.trending.ui

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhangzhu95.core.networking.Response
import com.zhangzhu95.domain.movies.FetchHomeSectionsUseCase
import com.zhangzhu95.domain.movies.models.HomeSections
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    fetchHomeSectionsUseCase: FetchHomeSectionsUseCase
) : ViewModel() {

    val viewState: StateFlow<HomeViewState> = fetchHomeSectionsUseCase.invoke()
        .map {
            it.toViewState()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            HomeViewState.Loading
        )

    @VisibleForTesting
    fun Response<HomeSections>.toViewState() = when (this) {
        is Response.Success -> HomeViewState.Sections(this.data!!)
        is Response.Error -> HomeViewState.Error(this.message)
        is Response.Loading -> HomeViewState.Loading
        else -> HomeViewState.Idle
    }
}
