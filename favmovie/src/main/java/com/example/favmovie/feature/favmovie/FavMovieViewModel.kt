package com.example.favmovie.feature.favmovie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.capstone.base.BaseViewModel
import com.example.core.data.Resource
import com.example.core.domain.model.Movie
import com.example.core.domain.usecase.MovieUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Named

class FavMovieViewModel @Inject constructor(@Named("FavMovieModule") private val useCase: MovieUseCase) : BaseViewModel() {

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