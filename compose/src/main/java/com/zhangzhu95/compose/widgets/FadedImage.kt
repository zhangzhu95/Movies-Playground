package com.zhangzhu95.compose.widgets

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import com.zhangzhu95.compose.R
import com.zhangzhu95.compose.widgets.styles.Blackish

@Composable
fun FadedImage(
    modifier: Modifier = Modifier,
    url: String,
    placeholderRes: Int = R.mipmap.movie_poster
) {
    RemoteImage(
        url = url,
        placeholderRes = placeholderRes,
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .drawWithContent {
                val colors = listOf(
                    Color.Transparent,
                    Blackish
                )
                drawContent()
                drawRect(
                    brush = Brush.verticalGradient(colors)
                )
            },
        contentScale = ContentScale.Crop
    )
}
