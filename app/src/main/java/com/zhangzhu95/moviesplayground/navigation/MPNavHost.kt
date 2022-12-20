package com.zhangzhu95.moviesplayground.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.zhangzhu95.details.ui.DetailsNavScreen
import com.zhangzhu95.search.SearchNavScreen
import com.zhangzhu95.trending.ui.HomeNavScreen

@Composable
fun MPNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = Destinations.home,

) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {

        composable(Destinations.home) {
            HomeNavScreen(
                onMovieSelected = navController::navigateToMovieDetails,
                onSearchClick = navController::navigateToSearch
            )
        }

        composable(Destinations.search) {
            SearchNavScreen(onMovieClicked = navController::navigateToMovieDetails)
        }

        composable(
            "${Destinations.details}?${Params.movieId}={${Params.movieId}}",
            arguments = listOf(navArgument(Params.movieId) { nullable = true })
        ) {
            DetailsNavScreen()
        }
    }
}

object Params {
    const val movieId = "movieId"
}

object Destinations {
    const val home = "home"
    const val search = "search"
    const val details = "details"
}

fun NavController.navigateToMovieDetails(id: Int) {
    navigate("${Destinations.details}?${Params.movieId}=$id")
}

fun NavController.navigateToSearch() {
    navigate(Destinations.search)
}