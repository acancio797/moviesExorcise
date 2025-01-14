package com.exorcise.movie.utils

import androidx.room.TypeConverter
import com.exorcise.movie.model.TypeMovieOrder
import java.util.Date

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }
}

fun String.toTypeMovieOrder(): TypeMovieOrder {
    return when (this) {
        "Popular" -> TypeMovieOrder.Popular
        "Top Rated" -> TypeMovieOrder.TopRated
        "Upcoming" -> TypeMovieOrder.Upcoming
        else -> TypeMovieOrder.Popular
    }
}