package com.example.capstone.util

import com.example.core.domain.model.Movie

object DummyData {
    fun getMovies(): List<Movie> {
        return listOf(Movie(
            adult = true,
            backdropPath = "/fEe5fe82qHzjO4yej0o79etqsWV.jpg",
            id = 629542,
            originalLanguage = "en",
            originalTitle = "The Bad Guys",
            overview = "When the infamous Bad Guys are finally caught after years of countless heists and being the world’s most-wanted villains",
            popularity = 5100.406,
            posterPath = "/7qop80YfuO0BwJa1uXk1DXUUEwv.jpg",
            releaseDate = "2022-03-17",
            title = "The Bad Guys",
            voteAverage = 7.7F,
            voteCount = 287,
        ))
    }
}