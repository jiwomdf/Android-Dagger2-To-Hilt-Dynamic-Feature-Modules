package com.programmergabut.core.data.remote.network

import com.programmergabut.core.data.remote.response.moviedetail.MovieDetailResponse
import com.programmergabut.core.data.remote.response.movies.MovieResponse
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiService {
    @GET("3/movie/upcoming")
    fun getMovieList(): Flowable<MovieResponse>

    @GET("3/search/movie?")
    fun getMovieByQuery(
        @Query("query") query: String
    ): Flowable<MovieResponse>

    @GET("3/movie/{movie_id}")
    fun getMovieDetail(
        @Path("movie_id") movieID: Int
    ): Flowable<MovieDetailResponse>

}
