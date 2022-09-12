package com.zhangzhu95.trending.ui

import androidx.navigation.NavController

interface TrendingNavigation {
    fun goToMovieDetails(navController: NavController, movieId: String)
}
