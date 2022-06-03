package com.programmergabut.favmovie.util

import com.programmergabut.core.domain.model.Movie

object DummyData {
    fun getMovies(): List<Movie> {
        return listOf(Movie(
            backdropPath = "/fEe5fe82qHzjO4yej0o79etqsWV.jpg",
            id = 629542,
            originalTitle = "The Bad Guys",
            overview = "When the infamous Bad Guys are finally caught after years of countless heists and being the world’s most-wanted villains",
            posterPath = "/7qop80YfuO0BwJa1uXk1DXUUEwv.jpg",
            title = "The Bad Guys",
        ))
    }
}