package com.zhangzhu95.core.ui.widgets

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zhangzhu95.core.R
import com.zhangzhu95.core.ui.widgets.styles.AppTheme

@Composable
fun MovieItem(
    id: Int = 0,
    postureUrl: String = "",
    onMovieClicked: (Int) -> Unit
) {
    TextButton(onClick = { onMovieClicked(id) }) {
        RemoteImage(
            url = postureUrl,
            R.mipmap.movie_poster,
            modifier = Modifier
                .wrapContentHeight()
                .width(150.dp)
                .height(200.dp)
                .clip(RoundedCornerShape(10.dp))
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MovieItemPreview() {
    AppTheme {
        Surface {
            MovieItem {}
        }
    }
}
