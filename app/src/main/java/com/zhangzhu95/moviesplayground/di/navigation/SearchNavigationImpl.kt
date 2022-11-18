package com.zhangzhu95.moviesplayground.di.navigation

import androidx.navigation.NavController
import com.zhangzhu95.search.SearchFragmentDirections
import com.zhangzhu95.search.SearchNavigation

object SearchNavigationImpl : SearchNavigation {

    override fun goToMovieDetails(navController: NavController, movieId: String) {
        navController.navigate(
            SearchFragmentDirections.actionSearchFragmentToDetailsFragment(
                movieId
            )
        )
    }
}
