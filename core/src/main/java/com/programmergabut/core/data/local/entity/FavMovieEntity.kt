package com.programmergabut.core.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fav_movie")
data class FavMovieEntity(
    @PrimaryKey
    val id: Int,
)