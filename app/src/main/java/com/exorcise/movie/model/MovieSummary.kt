package com.exorcise.movie.model

import androidx.annotation.Keep
import androidx.room.Entity
import java.util.*

@Entity(
    tableName = "movies",
    primaryKeys = ["id"]
)
@Keep
data class MovieSummary(val id: Int,
                        val title: String,
                        val rating: Float,
                        val releaseDate: Date,
                        val imageUrl: String)
