package com.zhangzhu95.core.ui.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.zhangzhu95.core.ui.widgets.styles.DarkerWhite
import com.zhangzhu95.core.ui.widgets.styles.LightBlackish

@Composable
fun Chip(text: String) {
    Text(
        text = text,
        Modifier
            .background(LightBlackish, shape = RoundedCornerShape(5.dp))
            .padding(vertical = 5.dp, horizontal = 10.dp),
        color = DarkerWhite
    )
}
