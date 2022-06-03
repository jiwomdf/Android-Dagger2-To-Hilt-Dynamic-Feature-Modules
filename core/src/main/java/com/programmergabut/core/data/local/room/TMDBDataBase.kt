package com.programmergabut.core.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.programmergabut.core.data.local.entity.FavMovieEntity
import com.programmergabut.core.data.local.entity.MovieDetailEntity
import com.programmergabut.core.data.local.entity.MovieEntity

@Database(entities = [MovieEntity::class, MovieDetailEntity::class, FavMovieEntity::class],
    version = 1, exportSchema = false)
abstract class TMDBDataBase: RoomDatabase() {
    abstract fun movieDao(): MovieDao
}