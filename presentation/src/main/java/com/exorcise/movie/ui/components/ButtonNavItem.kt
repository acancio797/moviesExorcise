package com.exorcise.movie.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Personal : BottomNavItem(
        route = "personal",
        title = "Personal",
        icon = Icons.Filled.Person
    )

    object Movies : BottomNavItem(
        route = "movies",
        title = "Movies",
        icon = Icons.Filled.List
    )

    object Map : BottomNavItem(
        route = "map",
        title = "Map",
        icon = Icons.Filled.Place
    )

    object Photos : BottomNavItem(
        route = "photos",
        title = "Photos",
        icon = Icons.Filled.CheckCircle
    )
}