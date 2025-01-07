package com.exorcise.movie.ui.components

import android.text.TextUtils.replace
import android.view.View
import android.widget.FrameLayout
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.*
import com.exorcise.movie.ui.map.MapFragment
import com.exorcise.movie.ui.movie.MovieFragment
import com.exorcise.movie.ui.person.PersonalFragment


@Composable
fun FragmentContainer(
    fragmentManager: FragmentManager,
    currentItem: BottomNavItem?,
    modifier: Modifier = Modifier
) {

    val containerId = remember { View.generateViewId() }

    LaunchedEffect(currentItem) {
        val fragment = when (currentItem) {
            BottomNavItem.Personal -> PersonalFragment()
            BottomNavItem.Movies -> MovieFragment()
            BottomNavItem.Map -> MapFragment()
            BottomNavItem.Photos -> PersonalFragment()
            else -> PersonalFragment()
        }
        fragmentManager.commit {
            replace(containerId, fragment)
        }
    }

    // AndroidView crea un FrameLayout sin necesidad de un layout XML
    AndroidView(
        modifier = modifier,
        factory = { context ->
            FrameLayout(context).apply { id = containerId }
        }
    )
}