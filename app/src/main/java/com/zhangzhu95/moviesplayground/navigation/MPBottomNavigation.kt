package com.zhangzhu95.moviesplayground.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.zhangzhu95.moviesplayground.R

val BottomNavigationItems = listOf(
    Screen.Home,
    Screen.Search
)

sealed class Screen(
    val route: String,
    @StringRes val resourceId: Int,
    val icon: ImageVector
) {
    object Home : Screen(Destinations.home, R.string.home, Icons.Filled.Home)
    object Search : Screen(Destinations.search, R.string.search, Icons.Filled.Search)
}