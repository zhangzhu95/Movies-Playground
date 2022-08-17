package com.zhangzhu95.moviesplayground.di.navigation

import androidx.navigation.NavController
import com.zhangzhu95.trending.ui.TrendingFragmentDirections
import com.zhangzhu95.trending.ui.TrendingNavigation

object TrendingNavigationImpl : TrendingNavigation {

    override fun goToMovieDetails(navController: NavController, movieId: String) {
        navController.navigate(
            TrendingFragmentDirections.actionTrendingFragmentToDetailsFragment(
                movieId
            )
        )
    }
}
