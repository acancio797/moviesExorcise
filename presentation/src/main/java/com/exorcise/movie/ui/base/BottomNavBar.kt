package com.exorcise.movie.ui.base

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.exorcise.movie.ui.components.BottomNavItem

@Composable
fun BottomNavBar(
    currentRoute: String,
    onItemSelected: (BottomNavItem) -> Unit
) {
    val bottomNavItems = remember {
        listOf(
            BottomNavItem.Personal,
            BottomNavItem.Movies,
            BottomNavItem.Map,
            BottomNavItem.Photos
        )
    }

    NavigationBar(
        modifier = Modifier
    ) {
        bottomNavItems.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.title) },
                label = { Text(text = item.title) },
                selected = currentRoute == item.route,
                onClick = { onItemSelected(item) }
            )
        }
    }
}
