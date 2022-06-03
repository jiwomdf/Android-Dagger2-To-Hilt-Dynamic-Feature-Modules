package com.programmergabut.core.domain.model

import androidx.room.PrimaryKey

data class FavMovie(
    @PrimaryKey
    val id: Int,
    val adult: Boolean,
    val backdropPath: String,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val releaseDate: String,
    val title: String,
    val voteAverage: Float,
    val voteCount: Int,
)