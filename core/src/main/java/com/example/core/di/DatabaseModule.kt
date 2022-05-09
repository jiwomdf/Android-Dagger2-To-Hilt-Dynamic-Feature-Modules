package com.example.core.di

import android.content.Context
import net.sqlcipher.database.SQLiteDatabase
import android.util.Log
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.core.data.local.room.MovieDao
import com.example.core.data.local.room.TMDBDataBase
import dagger.Module
import dagger.Provides
import net.sqlcipher.database.SupportFactory
import java.util.concurrent.Executors
import javax.inject.Singleton

@Module
class DatabaseModule {

    private val passphrase: ByteArray = SQLiteDatabase.getBytes("TMDBDataBase".toCharArray())
    val factory = SupportFactory(passphrase)

    @Singleton
    @Provides
    fun provideDatabase(context: Context): TMDBDataBase = Room.databaseBuilder(
        context,
        TMDBDataBase::class.java, "TMDBDataBase.db"
    )
        .fallbackToDestructiveMigration()
        .setQueryCallback({ sqlQuery, bindArgs ->
            Log.e("TMDBDataBase", "provideDatabase: SQL Query: $sqlQuery SQL Args: $bindArgs")
        }, Executors.newSingleThreadExecutor())
        .openHelperFactory(factory)
        .build()

    @Provides
    fun provideTourismDao(database: TMDBDataBase): MovieDao = database.movieDao()

}