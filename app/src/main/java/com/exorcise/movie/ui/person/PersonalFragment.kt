package com.exorcise.movie.ui.person

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material3.Card
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.exorcise.movie.R
import com.exorcise.movie.model.PersonDetails
import com.exorcise.movie.ui.LocalDateFormatter
import com.exorcise.movie.ui.LocalTimeFormatter
import com.exorcise.movie.ui.components.Rating
import com.exorcise.movie.utils.DateFormatter
import com.exorcise.movie.utils.TimeFormatter
import java.util.Date


class PersonalFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return ComposeView(requireContext()).apply {
            setContent {
                PersonalFragmentUI(viewModel = hiltViewModel())
            }
        }
    }


    @Composable
    fun PersonalFragmentUI(viewModel: PersonalViewModel) {
        PersonalRoute(viewModel = viewModel)
    }


}

@Composable
fun PersonalFragmentHome(
    personDetails: PersonDetails?,
    modifier: Modifier = Modifier
) {

    MaterialTheme {
        DetailsScaffold(
            modifier = modifier,
        ) { paddingValues ->
            Details(modifier = Modifier.padding(paddingValues), person = personDetails!!)
        }

    }
}

@Composable
fun DetailsScaffold(
    modifier: Modifier = Modifier,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
        },
        content = content
    )
}

@Composable
fun Header(
    modifier: Modifier = Modifier,
    backdropUrl: String,
    posterUrl: String
) {
    val backdropAspectRatio = 1.78f
    val crossfadeTransitionMilis = 500

    Box(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        contentAlignment = Alignment.CenterStart
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(ratio = backdropAspectRatio)
                .wrapContentHeight(),
            model = ImageRequest.Builder(LocalContext.current)
                .data(backdropUrl)
                .crossfade(durationMillis = crossfadeTransitionMilis)
                .build(),
            contentScale = ContentScale.FillWidth,
            contentDescription = null,
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(ratio = backdropAspectRatio)
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            androidx.compose.material3.MaterialTheme.colorScheme.primary,
                            Color.Transparent
                        )
                    )
                )
        ) {
            AsyncImage(
                modifier = Modifier
                    .padding(top = 16.dp, bottom = 16.dp, start = 16.dp)
                    .wrapContentWidth()
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(4.dp)),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(posterUrl)
                    .crossfade(crossfadeTransitionMilis)
                    .build(),
                contentScale = ContentScale.Fit,
                contentDescription = null,
            )
        }
    }
}

@Composable
fun Tagline(
    modifier: Modifier = Modifier,
    tagline: String
) {
    Text(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(color = androidx.compose.material3.MaterialTheme.colorScheme.primary)
            .padding(top = 16.dp, bottom = 16.dp),
        style = androidx.compose.material3.MaterialTheme.typography.bodyMedium.copy(
            fontStyle = FontStyle.Italic,
            color = androidx.compose.material3.MaterialTheme.colorScheme.onPrimary
        ),
        text = tagline,
        textAlign = TextAlign.Center
    )
}


@Composable
fun Details(
    modifier: Modifier = Modifier,
    person: PersonDetails,
    dateFormatter: DateFormatter = LocalDateFormatter.current,
    timeFormatter: TimeFormatter = LocalTimeFormatter.current
) {
    LazyColumn(
        modifier = modifier
    ) {
        item {
            Header(
                backdropUrl = person.profilePath ?: "",
                posterUrl = person.profilePath ?: ""
            )
        }

        if (person.name.isNotBlank()) {
            item {
                Tagline(
                    tagline = person.name
                )
            }
        }
        item {
            Highlights(
                rating = person.popularity,
                birtDate = person.birthday ?: "",
                alsoKnowsAs = person.alsoKnownAs,
                dateFormatter = dateFormatter,
                timeFormatter = timeFormatter
            )
        }
        item {
            Overview(overview = person.biography)
        }
    }
}


@Composable
fun Highlights(
    modifier: Modifier = Modifier,
    rating: Double,
    birtDate: String,
    alsoKnowsAs: List<String>,
    dateFormatter: DateFormatter = LocalDateFormatter.current,
    timeFormatter: TimeFormatter = LocalTimeFormatter.current
) {
    Card(
        modifier = modifier,
        shape = RectangleShape
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(top = 16.dp, bottom = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Rating(
                modifier = Modifier.height(20.dp),
                rating = rating,
                style = androidx.compose.material3.MaterialTheme.typography.labelLarge,
                color = androidx.compose.material3.MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = birtDate,
                    style = androidx.compose.material3.MaterialTheme.typography.labelMedium
                )
                Spacer(modifier = Modifier.width(2.dp))
                Text(
                    text = stringResource(id = R.string.time_separator_char),
                    style = androidx.compose.material3.MaterialTheme.typography.labelMedium
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                contentPadding = PaddingValues(start = 16.dp, end = 16.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                itemsIndexed(items = alsoKnowsAs) { index, genre ->
                    val text = if (index < alsoKnowsAs.size - 1) {
                        "$genre, "
                    } else {
                        genre
                    }

                    Text(
                        text = text,
                        style = androidx.compose.material3.MaterialTheme.typography.labelMedium.copy(
                            color = androidx.compose.material3.MaterialTheme.colorScheme.primary
                        )
                    )
                }
            }
        }
    }
}


@Composable
fun Rating(
    modifier: Modifier = Modifier,
    rating: Double,
    style: TextStyle = LocalTextStyle.current,
    color: Color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.wrapContentSize(),
            text = rating.toString(),
            style = style.copy(color = color)
        )
    }
}

@Composable
fun Overview(
    modifier: Modifier = Modifier,
    overview: String
) {
    Column(
        modifier = modifier.padding(
            start = 16.dp,
            bottom = 16.dp,
            end = 16.dp,
            top = 32.dp
        )
    ) {

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.personal_details_overview_title),
            style = androidx.compose.material3.MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Start
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = overview,
            style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Start
        )
    }
}
