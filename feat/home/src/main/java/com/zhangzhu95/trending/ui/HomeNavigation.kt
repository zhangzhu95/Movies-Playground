package com.zhangzhu95.trending.ui

import androidx.navigation.NavController

interface HomeNavigation {
    fun goToMovieDetails(navController: NavController, movieId: String)
    fun goToSearch(navController: NavController)
}
