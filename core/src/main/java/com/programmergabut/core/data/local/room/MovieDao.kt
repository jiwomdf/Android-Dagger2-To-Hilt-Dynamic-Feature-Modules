package com.programmergabut.core.data.local.room

import androidx.room.*
import com.programmergabut.core.data.local.entity.FavMovieEntity
import com.programmergabut.core.data.local.entity.MovieDetailEntity
import com.programmergabut.core.data.local.entity.MovieEntity
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface MovieDao {

    @Query("SELECT * FROM movie")
    fun getMovies(): Single<List<MovieEntity>>

    @Query("SELECT * FROM fav_movie")
    fun getFavMovies(): Single<List<Int>>

    @Query("SELECT * FROM movie_detail WHERE id = :id")
    fun getMovieDetail(id: Int): Single<MovieDetailEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(result: List<MovieEntity>): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMoviesDetail(result: MovieDetailEntity): Completable

    @Query("DELETE FROM movie")
    fun deleteMovies() : Completable

    @Query("DELETE FROM movie_detail WHERE id = :id")
    fun deleteMovieDetail(id: Int) : Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavMovieID(movieID: FavMovieEntity): Completable

    @Delete
    fun deleteFavMoviesID(movieID: FavMovieEntity): Completable
}