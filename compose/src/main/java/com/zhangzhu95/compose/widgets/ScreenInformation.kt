package com.zhangzhu95.compose.widgets

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zhangzhu95.compose.R

@Composable
fun ScreenInformation(
    modifier: Modifier = Modifier,
    @StringRes message: Int,
    @DrawableRes icon: Int
) {

    Column(modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = "Icon",
            Modifier.size(100.dp)
        )
        Spacing.Vertical.Small()
        Text(
            text = stringResource(id = message),
            color = Color.Gray,
            style = MaterialTheme.typography.h5
        )
    }
}

@Preview
@Composable
private fun ScreenInformationPreview() {
    ScreenInformation(
        message = R.string.placeholder_title,
        icon = R.drawable.ic_baseline_search_24
    )
}
