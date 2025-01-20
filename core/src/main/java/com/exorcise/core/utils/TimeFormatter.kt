package com.exorcise.core.utils

object TimeFormatter {
    fun formatMinutesInHours(minutes: Int): String {
        return "${minutes / 60}h ${minutes % 60}m"
    }
}
