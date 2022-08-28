package com.zhangzhu95.moviesplayground.di.navigation

import androidx.navigation.NavController
import com.zhangzhu95.trending.ui.HomeFragmentDirections
import com.zhangzhu95.trending.ui.HomeNavigation

object HomeNavigationImpl : HomeNavigation {

    override fun goToMovieDetails(navController: NavController, movieId: String) {
        navController.navigate(
            HomeFragmentDirections.actionHomeFragmentToDetailsFragment(
                movieId
            )
        )
    }
}
