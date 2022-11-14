package com.zhangzhu95.compose.widgets

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage

@Composable
fun RemoteImage(
    modifier: Modifier = Modifier,
    url: String,
    placeholderRes: Int,
    contentScale: ContentScale? = null
) {
    if (LocalInspectionMode.current) {
        Image(
            painter = painterResource(id = placeholderRes),
            contentDescription = "Image",
            modifier = modifier,
            contentScale = contentScale ?: ContentScale.None
        )
    } else {
        AsyncImage(
            model = url,
            "Image",
            modifier = modifier,
            contentScale = contentScale ?: ContentScale.None
        )
    }
}
