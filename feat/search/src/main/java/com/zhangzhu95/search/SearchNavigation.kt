package com.zhangzhu95.search

import androidx.navigation.NavController

interface SearchNavigation {
    fun goToMovieDetails(navController: NavController, movieId: String)
}