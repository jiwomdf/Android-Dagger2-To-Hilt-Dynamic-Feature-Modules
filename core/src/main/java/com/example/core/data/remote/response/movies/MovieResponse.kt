package com.example.core.data.remote.response.movies

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class MovieResponse(
    @field:SerializedName("page")
    val page: Int,
    @field:SerializedName("results")
    val results: List<MovieItemResponse>
)