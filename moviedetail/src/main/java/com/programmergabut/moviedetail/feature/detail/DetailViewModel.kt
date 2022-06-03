package com.programmergabut.moviedetail.feature.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.programmergabut.movieapp.base.BaseViewModel
import com.programmergabut.core.data.Resource
import com.programmergabut.core.domain.model.Movie
import com.programmergabut.core.domain.model.MovieDetail
import com.programmergabut.core.domain.usecase.MovieUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Named

class DetailViewModel @Inject constructor(@Named("MovieDetailModule") private val useCase: MovieUseCase) : BaseViewModel() {
    private val _moviesDetail = MutableLiveData<Resource<MovieDetail>>()
    val moviesDetail = _moviesDetail as LiveData<Resource<MovieDetail>>
    fun getMovieByID(id: Int){
      collect(
          useCase.getMovieDetail(id)
              .subscribeOn(Schedulers.io())
              .observeOn(AndroidSchedulers.mainThread())
              .subscribe({ response ->
                  _moviesDetail.postValue(response)
              }, { error ->
                  _moviesDetail.postValue(Resource.Error(error.localizedMessage ?: ""))
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

    fun insertFavMovie(movieID: Int) = useCase.insertFavMovieID(movieID)
    fun deleteFavMovie(movieID: Int) = useCase.deleteFavMovieID(movieID)
}