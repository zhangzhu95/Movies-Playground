package com.example.core.ui.widgets

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import coil.compose.rememberAsyncImagePainter

@Composable
fun PreviewImage(str: String, realImage: () -> Unit) {
    return if (LocalInspectionMode.current)
        painterResource(previewRes)
    else
        realImage()
}