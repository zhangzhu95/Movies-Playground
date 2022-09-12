package com.zhangzhu95.core.ui.widgets.animations

import androidx.compose.foundation.lazy.LazyListState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.math.abs

class SnapLazyRowDecorator constructor(
    var isDirty: Boolean,
    val coroutineScope: CoroutineScope,
    val lazyState: LazyListState,
    val onChange: (Boolean) -> Unit
) {

    init {
        lazyState.layoutInfo.visibleItemsInfo.minByOrNull { abs(it.offset) }?.let {
            //Timber.d("zacklog , offset ${it.offset} - title : ${state.list[it.index].title}")

            if (lazyState.isScrollInProgress)
                onChange(true)

            if (!lazyState.isScrollInProgress && isDirty) {
                coroutineScope.launch {
                    lazyState.animateScrollToItem(it.index, 0)
                    onChange(false)
                }
            }
        }
    }
}