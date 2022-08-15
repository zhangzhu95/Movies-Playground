package com.zhangzhu95.core.ui.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zhangzhu95.core.ui.compose.AppTheme

@Suppress("FunctionNaming")
@Composable
fun LoadingView(text: String? = null) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        CircularProgressIndicator(
            modifier = Modifier.padding(20.dp)
        )

        if (text != null)
            Text(text = text)
    }
}

@Preview(showBackground = true)
@Composable
fun previewLoadingView() {
    AppTheme {
        LoadingView()
    }
}
