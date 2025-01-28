package com.exorcise.movie.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.exorcise.domain.utils.DateFormatter
import com.exorcise.domain.utils.TimeFormatter
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.exorcise.movie.ui.theme.TrendytTheme

@Composable
fun MovieApp() {
    val useDarkTheme = isSystemInDarkTheme()

    MovieAppFoundation(useDarkTheme = useDarkTheme) {
        val systemUiController = rememberSystemUiController()
        SideEffect {
            systemUiController.setSystemBarsColor(Color.Transparent, darkIcons = !useDarkTheme)
        }

        Row(
            Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
                .statusBarsPadding()
                .windowInsetsPadding(
                    WindowInsets
                        .navigationBars
                        .only(WindowInsetsSides.Horizontal + WindowInsetsSides.Top)
                )
        ) {
            MovieNavGraph()
        }
    }

}

@Composable
fun MovieAppFoundation(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
    CompositionLocalProvider(
        LocalDateFormatter provides DateFormatter,
        LocalTimeFormatter provides TimeFormatter
    ) {
        TrendytTheme(useDarkTheme = useDarkTheme, content = content)
    }
}
