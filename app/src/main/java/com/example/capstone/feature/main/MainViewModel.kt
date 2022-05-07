package com.example.capstone.feature.main

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

class MainViewModel @Inject constructor(@Named("AppModule") private val useCase: MovieUseCase) : BaseViewModel() {
    private val _movies = MutableLiveData<Resource<List<Movie>>>()
    val movies = _movies as LiveData<Resource<List<Movie>>>
    fun getMovies(){
        collect(
            useCase.getMovies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    _movies.postValue(response)
                }, { error ->
                    _movies.postValue(Resource.Error(error.localizedMessage ?: ""))
                })
        )
    }

    fun getMoviesByQuery(query: String){
        collect(
            useCase.getMoviesByQuery(query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    _movies.postValue(response)
                }, { error ->
                    _movies.postValue(Resource.Error(error.localizedMessage ?: ""))
                })
        )
    }

    private val _favMovies = MutableLiveData<Resource<List<Movie>>>()
    val favMovies = _favMovies as LiveData<Resource<List<Movie>>>
    fun getFavMMovies(){
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