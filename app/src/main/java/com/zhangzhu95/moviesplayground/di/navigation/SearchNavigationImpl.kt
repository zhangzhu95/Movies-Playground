package com.zhangzhu95.moviesplayground.di.navigation

import androidx.navigation.NavController
import com.zhangzhu.search.SearchFragmentDirections
import com.zhangzhu.search.SearchNavigation

object SearchNavigationImpl : SearchNavigation {

    override fun goToMovieDetails(navController: NavController, movieId: String) {
        navController.navigate(
            SearchFragmentDirections.actionSearchFragmentToDetailsFragment(
                movieId
            )
        )
    }
}
