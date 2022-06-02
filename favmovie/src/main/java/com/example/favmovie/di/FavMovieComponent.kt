package com.example.favmovie.di

import android.content.Context
import com.example.capstone.di.SubModuleDependencies
import com.example.favmovie.feature.favmovie.FavMovieActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(
    dependencies = [SubModuleDependencies::class],
    modules = [FavMovieViewModelModule::class]
)
@Singleton
interface FavMovieComponent {

    fun inject(activity: FavMovieActivity)

    @Component.Builder
    interface Builder {
        fun context(@BindsInstance context: Context): Builder
        fun dependencies(component: SubModuleDependencies): Builder
        fun build(): FavMovieComponent
    }
}