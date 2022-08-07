package com.example.core.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

abstract class StateViewModel<VS : ViewState>(initialState: VS) : ViewModel() {

    abstract val viewState: Flow<VS>

}

