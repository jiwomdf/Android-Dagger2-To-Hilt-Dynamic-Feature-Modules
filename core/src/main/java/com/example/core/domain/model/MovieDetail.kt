package com.example.core.domain.model

data class MovieDetail (
    val backdropPath: String,
    val id: Int,
    val imdbId: String,
    val overview: String,
    val tagline: String,
    val title: String,
    val voteAverage: Double,
)