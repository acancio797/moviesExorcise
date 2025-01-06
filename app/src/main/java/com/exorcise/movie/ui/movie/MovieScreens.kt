package com.exorcise.movie.ui.movie

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.exorcise.movie.R
import com.exorcise.movie.model.MovieSummary
import com.exorcise.movie.ui.LocalDateFormatter
import com.exorcise.movie.ui.MovieAppFoundation
import com.exorcise.movie.ui.components.Rating
import com.exorcise.movie.ui.components.RetryScreen
import com.exorcise.movie.utils.DateFormatter
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieFeedScreen(
    modifier: Modifier = Modifier,
    uiState: HomeUiState.HasMovies,
    moviesLazyListState: LazyListState,
    onGetMovies: () -> Unit,
    onGetPopularTv: () -> Unit,
    onSelectMovie: (Int) -> Unit,
    onSelectType:Int,
) {

    HomeScaffold(
        modifier = modifier,
        lazyListState = moviesLazyListState,
    ) { contentPadding ->
        Column(
        ) {
            Row (
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .padding(top = 12.dp)
            ){
                    Surface(
                        shape = CircleShape,
                        modifier = Modifier
                            .padding(horizontal = 2.dp)
                            .width(0.dp)
                            .height(29.dp)
                            .weight(1F)
                            .clip(CircleShape),
                        color = if(onSelectType == 0)androidx.compose.ui.graphics.Color.Red else androidx.compose.ui.graphics.Color.Gray,
                        onClick = {
                            onGetMovies()
                        },
                    ) {

                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentSize(Alignment.Center),
                            text = "Movies",
                            color = androidx.compose.ui.graphics.Color.White,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }

                Surface(
                    shape = CircleShape,
                    modifier = Modifier
                        .padding(horizontal = 2.dp)
                        .width(0.dp)
                        .height(29.dp)
                        .weight(1F)
                        .clip(CircleShape),
                    color = if(onSelectType == 1)androidx.compose.ui.graphics.Color.Red else androidx.compose.ui.graphics.Color.Gray,
                    onClick = {
                        onGetPopularTv()
                    },
                ) {

                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentSize(Alignment.Center),
                        text = "Tv Series",
                        color = androidx.compose.ui.graphics.Color.White,
                        overflow = TextOverflow.Ellipsis,
                    )
                }


            }
            Spacer(modifier = Modifier.height(8.dp))
            SwipeRefresh(
                modifier = modifier.padding(
                    WindowInsets.systemBars
                        .only(WindowInsetsSides.Bottom)
                        .add(WindowInsets(top = contentPadding.calculateTopPadding()))
                        .asPaddingValues()
                ),
                state = rememberSwipeRefreshState(uiState.isRefreshing),
                indicator = { state, refreshTriggerDistance ->
                    SwipeRefreshIndicator(
                        state = state,
                        refreshTriggerDistance = refreshTriggerDistance,
                        contentColor = MaterialTheme.colorScheme.primary
                    )
                },
                onRefresh = onGetMovies
            ) {
                LazyColumn(
                    state = moviesLazyListState
                ) {
                    itemsIndexed(items = uiState.moviesFeed) { _, item ->
                        MovieItem(movieSummary = item, onSelect = onSelectMovie)
                    }
                }
            }

        }
    }

}

@Composable
fun NoMoviesScreen(
    modifier: Modifier = Modifier,
    uiState: HomeUiState.NoMovies,
    onGetMovies: () -> Unit,
) {
    HomeScaffold(modifier = modifier) {
        when (uiState.hasError) {
            true -> {
                RetryScreen(
                    modifier = Modifier.fillMaxSize(),
                    messageStyle = MaterialTheme.typography.labelLarge
                ) {
                    onGetMovies()
                }
            }
            false -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    if (uiState.isRefreshing) {
                        LinearProgressIndicator()
                    } else {
                        EmptyNotice()
                    }
                }
            }
        }
    }
}

@Composable
fun MovieItem(
    movieSummary: MovieSummary,
    onSelect: (Int) -> Unit,
    dateFormatter: DateFormatter = LocalDateFormatter.current
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(20.dp, 8.dp)
            .clickable {
                onSelect(movieSummary.id)
            }
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
        ) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(ratio = 1.78f)
                    .wrapContentHeight()
                    .background(color = MaterialTheme.colorScheme.secondaryContainer),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(movieSummary.imageUrl)
                    .placeholder(R.drawable.movie)
                    .crossfade(500)
                    .build(),
                contentScale = ContentScale.FillWidth,
                contentDescription = null,
            )

            Column(
                modifier = Modifier.padding(8.dp, 12.dp)
            ) {
                Text(
                    text = movieSummary.title,
                    style = MaterialTheme.typography.titleMedium
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = dateFormatter.formatDefaultDate(movieSummary.releaseDate),
                        style = MaterialTheme.typography.labelMedium
                    )

                    Rating(
                        modifier = Modifier.height(16.dp),
                        rating = movieSummary.rating,
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }
        }
    }
}

@Composable
fun EmptyNotice(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Image(
            modifier = Modifier.size(100.dp),
            painter = painterResource(id = R.drawable.movie),
            contentDescription = null,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
        )
        Spacer(Modifier.height(8.dp))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScaffold(
    modifier: Modifier = Modifier,
    lazyListState: LazyListState? = null,
    content: @Composable (PaddingValues) -> Unit,
) {

    Scaffold(
        modifier = modifier,
        content = content,
    )
}

val LazyListState?.isScrolled: Boolean
    get() = this?.let {
        firstVisibleItemIndex > 0 || firstVisibleItemScrollOffset > 0
    } ?: false

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewMovieFeedScreen() {
    MovieAppFoundation {
        MovieFeedScreen(
            uiState = HomeUiState.HasMovies(
                moviesFeed = (1..5).map { seed ->
                    MovieSummary(
                        id = seed,
                        title = "Movie $seed",
                        rating = 10f - seed,
                        releaseDate = GregorianCalendar(2022 - seed, seed, seed).time,
                        imageUrl = ""
                    )
                },
                isRefreshing = false,
                selected = 0,
                errorMessages = emptyList()
            ),
            moviesLazyListState = rememberLazyListState(),
            onGetMovies = {},
            onGetPopularTv = {},
            onSelectMovie = {},
            onSelectType = 0
        )

    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewNoMoviesScreenWithLoading() {
    MovieAppFoundation {
        NoMoviesScreen(
            uiState = HomeUiState.NoMovies(
                isRefreshing = true,
                selected = 0,
                errorMessages = emptyList()
            )
        ) {
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewNoMoviesScreenWithError() {
    MovieAppFoundation {
        NoMoviesScreen(
            uiState = HomeUiState.NoMovies(
                isRefreshing = false,
                selected = 0,
                errorMessages = listOf("some error")
            )
        ) {
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewHomeScaffold() {
    MovieAppFoundation {
        HomeScaffold {
            Text(
                modifier = Modifier.padding(it),
                text = "Some content"
            )
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewMovieItem() {
    MovieAppFoundation {
        MovieItem(
            movieSummary = MovieSummary(
                id = 1,
                title = "Title",
                rating = 8.5f,
                releaseDate = GregorianCalendar(2022, Calendar.MARCH, 1).time,
                imageUrl = ""
            ),
            onSelect = {}
        )
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewEmptyNotice() {
    MovieAppFoundation {
        EmptyNotice()
    }
}
