package com.exorcise.movie.ui.base

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.exorcise.movie.ui.components.RetryScreen
import com.exorcise.movie.ui.movie.EmptyNotice
import com.exorcise.movie.ui.movie.HomeScaffold
import com.exorcise.movie.ui.movie.MovieUiState

@Composable
fun LoadingMoviesScreen(
    modifier: Modifier = Modifier,
) {
    HomeScaffold(modifier = modifier) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            LinearProgressIndicator()
        }
    }
}