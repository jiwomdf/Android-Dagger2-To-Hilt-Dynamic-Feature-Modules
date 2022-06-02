package com.example.core.domain.usecase

import com.example.core.domain.repository.IMovieRepository
import javax.inject.Inject

class MovieInteractor @Inject constructor(private val movieRepository: IMovieRepository): MovieUseCase {

    override fun getMovies() = movieRepository.getMovies()

    override fun getMoviesByQuery(query: String) = movieRepository.getMoviesByQuery(query)

    override fun getMovieDetail(id: Int) = movieRepository.getMovieDetail(id)

    override fun getFavMovies() = movieRepository.getFavMovies()

    override fun insertFavMovieID(movieID: Int) = movieRepository.insertFavMovieID(movieID)

    override fun deleteFavMovieID(movieID: Int) = movieRepository.deleteFavMovieID(movieID)
}