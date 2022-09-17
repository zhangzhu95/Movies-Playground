package com.zhangzhu.search

import androidx.navigation.NavController

interface SearchNavigation {
    fun goToMovieDetails(navController: NavController, movieId: String)
}
