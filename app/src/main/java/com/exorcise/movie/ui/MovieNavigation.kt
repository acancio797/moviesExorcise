package com.exorcise.movie.ui

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

object MovieDestinations {
    val HOME_ROUTE = NavHomeRoute("home")
    val MOVIE_ROUTE =
        NavMovieRoute("movie/{id}", navArgument("id") { type = NavType.IntType })
    val MAP_ROUTE = NavMapRoute(route = "map")
    val PHOTO_ROUTE = NavPhotoRoute(route = "photo")
    val PROFILE_ROUTE = NavProfileRoute(route = "profile")
    val MAIN_ROUTE = NavHomeHome(route = "main")
}

sealed class NavRoute(val route: String, val arguments: List<NamedNavArgument>) {
    protected fun <T> routeWithPathArgumentValue(navArgument: NamedNavArgument, value: T): String {
        return route.replace("{${navArgument.name}}", value.toString())
    }
}

class NavHomeRoute(route: String) : NavRoute(route, emptyList())
class NavMovieRoute(route: String, val id: NamedNavArgument) : NavRoute(route, listOf(id)) {
    fun build(movieId: Int) = routeWithPathArgumentValue(id, movieId)
}
class NavMapRoute(route:String):NavRoute(route, emptyList())
class NavPhotoRoute(route: String):NavRoute(route, emptyList())
class NavProfileRoute(route: String):NavRoute(route, emptyList())
class NavProfileHome(route: String):NavRoute(route, emptyList())
class NavHomeHome(route: String):NavRoute(route, emptyList())
