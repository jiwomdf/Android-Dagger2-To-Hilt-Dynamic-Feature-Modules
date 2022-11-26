package com.programmergabut.core.domain.repository

import com.programmergabut.core.data.Resource
import com.programmergabut.core.domain.model.MovieDetail
import com.programmergabut.core.domain.model.Movie
import com.programmergabut.core.domain.model.Notification
import io.reactivex.Flowable

interface IMovieRepository {
    fun getMovies(): Flowable<Resource<List<Movie>>>
    fun getMoviesByQuery(query: String): Flowable<Resource<List<Movie>>>
    fun getMovieDetail(id: Int): Flowable<Resource<MovieDetail>>
    fun getFavMovies(): Flowable<Resource<List<Movie>>>
    fun insertFavMovieID(movieID: Int)
    fun deleteFavMovieID(movieID: Int)
    fun getListNotification(): Flowable<List<Notification>>
}