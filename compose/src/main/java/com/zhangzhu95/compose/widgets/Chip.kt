package com.zhangzhu95.compose.widgets

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zhangzhu95.compose.R
import com.zhangzhu95.compose.widgets.styles.DarkerWhite
import com.zhangzhu95.compose.widgets.styles.LightBlackish

@Composable
fun Chip(
    modifier: Modifier = Modifier,
    text: String,
    @DrawableRes icon: Int? = null
) {
    Row(
        modifier
            .background(LightBlackish, shape = RoundedCornerShape(5.dp))
            .padding(vertical = 5.dp, horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        icon?.let {
            Image(
                painter = painterResource(id = icon),
                contentDescription = "icon",
                modifier = Modifier.size(20.dp)
            )
            Spacing.Horizontal.Tiny()
        }

        Text(
            text = text,
            color = DarkerWhite,
        )
    }
}

@Preview
@Composable
private fun ChipPreview() {
    Chip(text = "Hola", icon = R.drawable.ic_baseline_search_24)
}
