package com.exorcise.movie.ui

import androidx.compose.runtime.staticCompositionLocalOf
import com.exorcise.core.utils.DateFormatter
import com.exorcise.core.utils.TimeFormatter

val LocalDateFormatter = staticCompositionLocalOf<com.exorcise.core.utils.DateFormatter> {
    error("DateFormatter not provided")
}

val LocalTimeFormatter = staticCompositionLocalOf<com.exorcise.core.utils.TimeFormatter> {
    error("TimeFormatter not provided")
}
