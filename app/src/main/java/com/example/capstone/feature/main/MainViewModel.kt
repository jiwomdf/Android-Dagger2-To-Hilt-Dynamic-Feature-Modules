package com.example.capstone.feature.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.capstone.base.BaseViewModel
import com.example.core.data.Resource
import com.example.core.domain.model.Movie
import com.example.core.domain.usecase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class MainViewModel @Inject constructor(private val useCase: MovieUseCase) : BaseViewModel() {
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
}