package com.zhangzhu95.compose.widgets

import androidx.compose.foundation.layout.height
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
import com.zhangzhu95.compose.R
import com.zhangzhu95.compose.themes.AppTheme

@Composable
fun MovieItem(
    modifier: Modifier = Modifier,
    id: Int = 0,
    posterUrl: String = "",
    onMovieClicked: (Int) -> Unit,
) {
    TextButton(modifier = modifier, onClick = { onMovieClicked(id) }) {
        RemoteImage(
            url = posterUrl,
            placeholderRes = R.mipmap.movie_poster,
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
private fun MovieItemPreview() {
    AppTheme {
        Surface {
            MovieItem {}
        }
    }
}
