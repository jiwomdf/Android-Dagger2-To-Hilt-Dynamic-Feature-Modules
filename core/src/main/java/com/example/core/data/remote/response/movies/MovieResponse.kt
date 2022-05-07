package com.example.core.data.remote.response.movies

import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @field:SerializedName("page")
    val page: Int,
    @field:SerializedName("results")
    val results: List<MovieItemResponse>
)