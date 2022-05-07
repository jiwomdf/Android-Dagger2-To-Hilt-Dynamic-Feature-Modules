package com.example.core.data.remote.response.movies

import com.google.gson.annotations.SerializedName

data class MovieItemResponse (
    @field:SerializedName("id")
    val id: Int,
    @field:SerializedName("adult")
    val adult: Boolean,
    @field:SerializedName("backdrop_path")
    val backdropPath: String?,
    @field:SerializedName("original_language")
    val originalLanguage: String?,
    @field:SerializedName("original_title")
    val originalTitle: String?,
    @field:SerializedName("overview")
    val overview: String?,
    @field:SerializedName("popularity")
    val popularity: Double,
    @field:SerializedName("poster_path")
    val posterPath: String?,
    @field:SerializedName("release_date")
    val releaseDate: String?,
    @field:SerializedName("title")
    val title: String?,
    @field:SerializedName("vote_average")
    val voteAverage: Float,
    @field:SerializedName("vote_count")
    val voteCount: Int,
)