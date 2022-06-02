package com.example.core.domain.model

data class Movie (
    val backdropPath: String,
    val id: Int,
    val originalTitle: String,
    val overview: String,
    val posterPath: String,
    val title: String,
)