package com.zhangzhu95.core.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow

abstract class StateViewModel<VS : ViewState>(initialState: VS) : ViewModel() {

    abstract val viewState: Flow<VS>

}

