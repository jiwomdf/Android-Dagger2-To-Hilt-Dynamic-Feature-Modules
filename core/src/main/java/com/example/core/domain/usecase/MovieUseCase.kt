package com.example.core.domain.usecase

import com.example.core.data.Resource
import com.example.core.domain.model.FavMovie
import com.example.core.domain.model.MovieDetail
import com.example.core.domain.model.Movie
import io.reactivex.Completable
import io.reactivex.Flowable


interface MovieUseCase {
    fun getMovies(): Flowable<Resource<List<Movie>>>
    fun getMoviesByQuery(query: String): Flowable<Resource<List<Movie>>>
    fun getMovieDetail(id: Int): Flowable<Resource<MovieDetail>>
    fun getFavMovies(): Flowable<Resource<List<Movie>>>
    fun insertFavMovieID(movieID: Int)
    fun deleteFavMovieID(movieID: Int)
}