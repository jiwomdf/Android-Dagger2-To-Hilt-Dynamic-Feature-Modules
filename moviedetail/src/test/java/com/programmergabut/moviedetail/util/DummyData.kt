package com.programmergabut.moviedetail.util

import com.programmergabut.core.domain.model.Movie
import com.programmergabut.core.domain.model.MovieDetail

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

    fun getMovieDetail(): MovieDetail {
        return MovieDetail(
                backdropPath = "/fEe5fe82qHzjO4yej0o79etqsWV.jpg",
                id = 123,
                imdbId = "tt0077869",
                overview = "The Fellowship of the Ring embark on a journey to destroy the One Ring and end Sauron's reign over Middle-earth.",
                tagline = "Fantasy...beyond your imagination",
                title = "The Lord of the Rings",
                voteAverage = 6.6,
        )
    }
}