package com.exorcise.movie.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.exorcise.movie.ui.MovieDestinations.MAIN_ROUTE
import com.exorcise.movie.ui.MovieDestinations.MOVIE_ROUTE
import com.exorcise.movie.ui.base.MainRoute
import com.exorcise.movie.ui.details.DetailsRoute

@Composable
fun MovieNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = MAIN_ROUTE.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(
            route = MAIN_ROUTE.route,
            arguments = MAIN_ROUTE.arguments
        ) {
            MainRoute(
                viewModel = hiltViewModel(),
            )
        }
        composable(
            route = MOVIE_ROUTE.route,
            arguments = MOVIE_ROUTE.arguments
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getInt(MOVIE_ROUTE.id.name)
            check(movieId != null)

            DetailsRoute(
                viewModel = hiltViewModel(),
                movieId = movieId,
                onTopBarBackPressed = {
                    navController.popBackStack()
                }
            )
        }
    }
}
