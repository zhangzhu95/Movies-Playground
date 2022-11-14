package com.zhangzhu95.compose.themes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

// Set of Material typography styles to start with
val Typography = Typography(
    h1 = TextStyle(
        fontSize = 40.sp,
        fontWeight = FontWeight.Bold
    ),
    h2 = TextStyle(
        fontSize = 30.sp,
        fontWeight = FontWeight.Bold
    ),
    h3 = TextStyle(
        fontSize = 26.sp,
        fontWeight = FontWeight.Bold
    ),
    h4 = TextStyle(
        fontSize = 22.sp,
        fontWeight = FontWeight.Bold
    ),
    h5 = TextStyle(
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold
    ),
    h6 = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold
    ),
    body1 = TextStyle(
        fontSize = 14.sp
    ),
    body2 = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Light,
        fontStyle = FontStyle.Italic,
    ),
    subtitle1 = TextStyle(
        fontSize = 17.sp,
        fontWeight = FontWeight.SemiBold
    ),
    subtitle2 = TextStyle(
        fontSize = 17.sp,
        fontStyle = FontStyle.Italic
    ),
)

@Preview
@Composable
private fun TypographyPreview() {
    MaterialTheme(
        typography = Typography
    ) {
        Row {
            Column {
                Text(text = "H1", style = MaterialTheme.typography.h1)
                Text(text = "H2", style = MaterialTheme.typography.h2)
                Text(text = "H3", style = MaterialTheme.typography.h3)
                Text(text = "H4", style = MaterialTheme.typography.h4)
                Text(text = "H5", style = MaterialTheme.typography.h5)
                Text(text = "H6", style = MaterialTheme.typography.h6)
            }

            Column {
                Text(text = "Body1", style = MaterialTheme.typography.body1)
                Text(text = "Body2", style = MaterialTheme.typography.body2)
                Text(text = "Subtitle1", style = MaterialTheme.typography.subtitle1)
                Text(text = "Subtitle2", style = MaterialTheme.typography.subtitle2)
            }
        }
    }
}
