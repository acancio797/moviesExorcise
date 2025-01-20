package com.exorcise.movie.ui.base

import MainScreen
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState


@Composable
fun MainRoute(
    viewModel: MainViewModel,
) {
    val lazyListState: LazyListState = rememberLazyListState()
    MainScreen()
}