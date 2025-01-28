package com.exorcise.domain.utils

object TimeFormatter {
    fun formatMinutesInHours(minutes: Int): String {
        return "${minutes / 60}h ${minutes % 60}m"
    }
}
