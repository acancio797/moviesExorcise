package com.exorcise.movie.ui

import androidx.compose.runtime.staticCompositionLocalOf
import com.exorcise.movie.utils.DateFormatter
import com.exorcise.movie.utils.TimeFormatter

val LocalDateFormatter = staticCompositionLocalOf<DateFormatter> {
    error("DateFormatter not provided")
}

val LocalTimeFormatter = staticCompositionLocalOf<TimeFormatter> {
    error("TimeFormatter not provided")
}
