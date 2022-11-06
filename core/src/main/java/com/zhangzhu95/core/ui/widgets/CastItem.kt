package com.zhangzhu95.core.ui.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zhangzhu95.core.R
import com.zhangzhu95.core.ui.widgets.styles.AppTheme

@Composable
fun CastItem(
    modifier: Modifier = Modifier,
    id: Int = 0,
    name: String,
    posterUrl: String = "",
    character: String,
    onClick: (Int) -> Unit
) {
    Column(modifier.width(120.dp)) {
        TextButton(onClick = { onClick(id) }) {
            RemoteImage(
                url = posterUrl,
                R.mipmap.movie_poster,
                modifier = Modifier
                    .wrapContentHeight()
                    .width(100.dp)
                    .height(120.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
        }
        Text(
            name,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            color = Color.White
        )
        Text(character, fontSize = 14.sp, fontStyle = FontStyle.Italic, color = Color.Gray)
    }
}

@Preview
@Composable
private fun CastItemPreview() {
    AppTheme {
        Surface {
            CastItem(name = "Morgan Freeman", character = "Jango") {}
        }
    }
}
