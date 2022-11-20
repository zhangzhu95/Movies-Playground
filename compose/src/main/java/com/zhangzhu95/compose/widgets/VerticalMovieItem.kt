package com.zhangzhu95.compose.widgets

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zhangzhu95.compose.themes.AppTheme
import com.zhangzhu95.compose.R

@Composable
fun VerticalMovieItem(
    id: Int = 0,
    title: String = "Title",
    postureUrl: String = "",
    rating: Double = 9.8,
    language: String = "EN",
    onMovieClicked: (Int) -> Unit
) {
    Box(Modifier.padding(horizontal = 10.dp)) {
        TextButton(onClick = { onMovieClicked(id) }) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                elevation = 5.dp
            ) {
                Row(modifier = Modifier.padding(10.dp)) {
                    RemoteImage(
                        url = postureUrl,
                        placeholderRes = R.mipmap.movie_poster,
                        modifier = Modifier
                            .wrapContentHeight()
                            .width(120.dp)
                            .height(150.dp)
                            .clip(RoundedCornerShape(10.dp))
                    )

                    Column(modifier = Modifier.padding(start = 10.dp)) {
                        Text(
                            title,
                            style = MaterialTheme.typography.h5
                        )

                        Row(modifier = Modifier.padding(top = 20.dp)) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_baseline_star_24),
                                contentDescription = "Rating",
                                tint = Color(0xFFD29B05)
                            )

                            Text(
                                rating.toString(),
                                color = Color(0xFFD29B05),
                                style = MaterialTheme.typography.subtitle1
                            )
                        }

                        Text(
                            modifier = Modifier.padding(top = 10.dp),
                            text = "Language : $language",
                            color = Color.Gray
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun VerticalMovieItemPreview() {
    AppTheme {
        Surface(
            modifier = Modifier.padding(20.dp)
        ) {
            VerticalMovieItem {}
        }
    }
}
