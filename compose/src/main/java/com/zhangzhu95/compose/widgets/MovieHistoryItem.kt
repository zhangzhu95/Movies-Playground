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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zhangzhu95.compose.R
import com.zhangzhu95.compose.themes.AppTheme

@Composable
fun MovieHistoryItem(
    modifier: Modifier = Modifier,
    id: Int = 0,
    postureUrl: String = "",
    onMovieClicked: (Int) -> Unit,
) {
    TextButton(modifier = modifier, onClick = { onMovieClicked(id) }) {
        RemoteImage(
            url = postureUrl,
            placeholderRes = R.mipmap.movie_poster,
            modifier = Modifier
                .width(75.dp)
                .height(100.dp)
                .clip(RoundedCornerShape(10.dp))
                .wrapContentHeight(),
            contentScale = ContentScale.FillBounds
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MovieHistoryItemPreview() {
    AppTheme {
        Surface {
            MovieItem {}
        }
    }
}
