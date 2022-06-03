package com.programmergabut.favmovie.feature.favmovie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.programmergabut.movieapp.base.BaseViewModel
import com.programmergabut.core.data.Resource
import com.programmergabut.core.domain.model.Movie
import com.programmergabut.core.domain.usecase.MovieUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class FavMovieViewModel @Inject constructor(private val useCase: MovieUseCase) : BaseViewModel() {

    private val _favMovies = MutableLiveData<Resource<List<Movie>>>()
    val favMovies = _favMovies as LiveData<Resource<List<Movie>>>
    fun getFavMovies(){
        collect(
            useCase.getFavMovies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    _favMovies.postValue(response)
                }, { error ->
                    _favMovies.postValue(Resource.Error(error.localizedMessage ?: ""))
                })
        )
    }
}