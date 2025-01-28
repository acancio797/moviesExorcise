package com.exorcise.movie.ui

import androidx.compose.runtime.staticCompositionLocalOf
import com.exorcise.domain.utils.DateFormatter
import com.exorcise.domain.utils.TimeFormatter

val LocalDateFormatter = staticCompositionLocalOf<DateFormatter> {
    error("DateFormatter not provided")
}

val LocalTimeFormatter = staticCompositionLocalOf<TimeFormatter> {
    error("TimeFormatter not provided")
}
