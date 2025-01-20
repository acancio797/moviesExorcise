package com.exorcise.movie.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.exorcise.movie.ui.MovieAppFoundation

@Composable
fun Rating(
    modifier: Modifier = Modifier,
    rating: Float,
    style: TextStyle = LocalTextStyle.current,
    color: Color = MaterialTheme.colorScheme.onSurface
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

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewRating() {
    MovieAppFoundation{
        Surface {
            Rating(
                modifier = Modifier.height(30.dp),
                rating = 8.1f
            )
        }
    }
}
