package com.example.core.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.core.data.local.entity.FavMovieEntity
import com.example.core.data.local.entity.MovieDetailEntity
import com.example.core.data.local.entity.MovieEntity

@Database(entities = [MovieEntity::class, MovieDetailEntity::class, FavMovieEntity::class],
    version = 1, exportSchema = false)
abstract class TMDBDataBase: RoomDatabase() {
    abstract fun movieDao(): MovieDao
}