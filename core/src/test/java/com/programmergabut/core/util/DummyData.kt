package com.programmergabut.core.util

import com.programmergabut.core.domain.model.Movie
import com.programmergabut.core.domain.model.MovieDetail

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

    fun getMovieDetail(): MovieDetail {
        return MovieDetail(
            adult= false,
            backdropPath = "/fEe5fe82qHzjO4yej0o79etqsWV.jpg",
            budget = 200,
            homepage = "",
            id = 123,
            imdbId = "tt0077869",
            originalLanguage = "en",
            originalTitle = "The Lord of the Rings",
            overview = "The Fellowship of the Ring embark on a journey to destroy the One Ring and end Sauron's reign over Middle-earth.",
            popularity = 26.194,
            posterPath = "/liW0mjvTyLs7UCumaHhx3PpU4VT.jpg",
            releaseDate = "1978-11-15",
            revenue = 30471420,
            runtime = 132,
            status = "Released",
            tagline = "Fantasy...beyond your imagination",
            title = "The Lord of the Rings",
            video = false,
            voteAverage = 6.6,
            voteCount = 647
        )
    }
}