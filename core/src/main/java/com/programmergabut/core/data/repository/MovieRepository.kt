package com.programmergabut.core.data.repository

import android.annotation.SuppressLint
import com.programmergabut.core.data.NetworkBoundResource
import com.programmergabut.core.data.Resource
import com.programmergabut.core.data.local.entity.FavMovieEntity
import com.programmergabut.core.data.local.entity.MovieDetailEntity
import com.programmergabut.core.data.local.entity.MovieEntity
import com.programmergabut.core.data.local.room.MovieDao
import com.programmergabut.core.data.remote.network.ApiResponse
import com.programmergabut.core.data.remote.network.MovieApiService
import com.programmergabut.core.data.remote.response.moviedetail.MovieDetailResponse
import com.programmergabut.core.data.remote.response.movies.MovieItemResponse
import com.programmergabut.core.domain.model.MovieDetail
import com.programmergabut.core.domain.model.Movie
import com.programmergabut.core.domain.repository.IMovieRepository
import io.reactivex.BackpressureStrategy
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(
    private val apiService: MovieApiService,
    private val movieDao: MovieDao
) : IMovieRepository {

    override fun getMovies(): Flowable<Resource<List<Movie>>> =
        object : NetworkBoundResource<List<Movie>, List<MovieItemResponse>>() {
            override fun loadFromDB(): Flowable<List<Movie>> {
                return movieDao.getMovies().toFlowable().map {
                    val data = it.map { movie ->
                        Movie(
                            backdropPath = movie.backdropPath,
                            id = movie.id,
                            originalTitle = movie.originalTitle,
                            overview = movie.overview,
                            posterPath = movie.posterPath,
                            title = movie.title,
                        )
                    }
                    data
                }
            }

            override fun shouldFetch(data: List<Movie>?): Boolean = true

            @SuppressLint("CheckResult")
            override fun createCall(): Flowable<ApiResponse<List<MovieItemResponse>>> {
                val resultData = PublishSubject.create<ApiResponse<List<MovieItemResponse>>>()

                apiService.getMovieList()
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .subscribe({ response ->
                        val dataArray = response.results
                        resultData.onNext(if (dataArray.isNotEmpty()) ApiResponse.Success(dataArray) else ApiResponse.Empty)
                    }, { error ->
                        resultData.onNext(ApiResponse.Error(error.message.toString()))
                    })

                return resultData.toFlowable(BackpressureStrategy.BUFFER)
            }

            @SuppressLint("CheckResult")
            override fun saveCallResult(data: List<MovieItemResponse>) {
                val result = data.map { input ->
                    MovieEntity(
                        adult = input.adult,
                        backdropPath = input.backdropPath ?: "",
                        id = input.id,
                        originalLanguage = input.originalLanguage ?: "",
                        originalTitle = input.originalTitle ?: "",
                        overview = input.overview ?: "",
                        popularity = input.popularity,
                        posterPath = input.posterPath ?: "",
                        releaseDate = input.releaseDate ?: "",
                        title = input.title ?: "",
                        voteAverage = input.voteAverage,
                        voteCount = input.voteCount,
                    )
                }

                Completable.mergeArray(
                    movieDao.deleteMovies(),
                    movieDao.insertMovies(result)
                )
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .blockingGet()
            }

        }.asFlowable()

    @SuppressLint("CheckResult")
    override fun getMoviesByQuery(query: String): Flowable<Resource<List<Movie>>> {
        val resultData = PublishSubject.create<Resource<List<Movie>>>()

        apiService.getMovieByQuery(query)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe({ response ->
                val dataArray = response.results.map {
                    Movie(
                        backdropPath = it.backdropPath ?: "",
                        id = it.id,
                        originalTitle = it.originalTitle ?: "",
                        overview = it.overview ?: "",
                        posterPath = it.posterPath ?: "",
                        title = it.title ?: "",
                    )
                }
                resultData.onNext(if (dataArray.isNotEmpty()) Resource.Success(dataArray) else Resource.Error(""))
            }, { error ->
                resultData.onNext(Resource.Error(error.message.toString()))
            })

        return resultData.toFlowable(BackpressureStrategy.BUFFER)
    }

    override fun getMovieDetail(id: Int): Flowable<Resource<MovieDetail>> =
        object : NetworkBoundResource<MovieDetail, MovieDetailResponse>() {
            override fun loadFromDB(): Flowable<MovieDetail> {
                return movieDao.getMovieDetail(id).toFlowable().map {
                    val data = it.let { movie ->
                        MovieDetail(
                            id = movie.id,
                            backdropPath = movie.backdropPath,
                            imdbId = movie.imdbId,
                            overview = movie.overview,
                            tagline = movie.tagline,
                            title = movie.title,
                            voteAverage = movie.voteAverage,
                        )
                    }
                    data
                }
            }

            override fun shouldFetch(data: MovieDetail?): Boolean = true

            @SuppressLint("CheckResult")
            override fun createCall(): Flowable<ApiResponse<MovieDetailResponse>> {
                val resultData = PublishSubject.create<ApiResponse<MovieDetailResponse>>()

                apiService.getMovieDetail(id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .subscribe({ response ->
                        resultData.onNext(ApiResponse.Success(response))
                    }, { error ->
                        resultData.onNext(ApiResponse.Error(error.message.toString()))
                    })

                return resultData.toFlowable(BackpressureStrategy.BUFFER)
            }

            @SuppressLint("CheckResult")
            override fun saveCallResult(data: MovieDetailResponse) {
                val result = data.let { input ->
                    MovieDetailEntity(
                        id = input.id,
                        adult = input.adult,
                        backdropPath = input.backdropPath,
                        budget = input.budget,
                        homepage = input.homepage,
                        imdbId = input.imdbId,
                        originalLanguage = input.originalLanguage,
                        originalTitle = input.originalTitle,
                        overview = input.overview,
                        popularity = input.popularity,
                        posterPath = input.posterPath,
                        releaseDate = input.releaseDate,
                        revenue = input.revenue,
                        runtime = input.runtime,
                        status = input.status,
                        tagline = input.tagline,
                        title = input.title,
                        video = input.video,
                        voteAverage = input.voteAverage,
                        voteCount = input.voteCount,
                    )
                }

                Completable.mergeArray(
                    movieDao.deleteMovieDetail(result.id),
                    movieDao.insertMoviesDetail(result)
                )
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .blockingGet()
            }

        }.asFlowable()

    override fun getFavMovies(): Flowable<Resource<List<Movie>>> {
        val favMoviesID = movieDao.getFavMovies()
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .blockingGet()

        val listFavMovie = mutableListOf<Movie>()
        for (i in favMoviesID.indices){
            movieDao.getMovieDetail(favMoviesID[i])
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .blockingGet()
                .let { movie ->
                    listFavMovie.add(
                        Movie(
                            backdropPath = movie.backdropPath,
                            id = movie.id,
                            originalTitle = movie.originalTitle,
                            overview = movie.overview,
                            posterPath = movie.posterPath,
                            title = movie.title,
                        )
                    )
                }
        }

        return Flowable.fromArray(Resource.Success(listFavMovie))
    }

    @SuppressLint("CheckResult")
    override fun insertFavMovieID(movieID: Int) {
        movieDao.insertFavMovieID(FavMovieEntity(movieID))
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .blockingGet()
    }

    @SuppressLint("CheckResult")
    override fun deleteFavMovieID(movieID: Int) {
        movieDao.deleteFavMoviesID(FavMovieEntity(movieID))
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .blockingGet()
    }

}