package com.example.moviedetail.feature.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.moviedetail.util.TrampolineSchedulerRule
import com.example.core.data.Resource
import com.example.core.domain.model.Movie
import com.example.core.domain.model.MovieDetail
import com.example.core.domain.usecase.MovieUseCase
import com.example.moviedetail.util.DummyData
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import io.reactivex.Flowable
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

class DetailViewModelTest {

    private lateinit var viewModel: DetailViewModel

    @Mock
    private lateinit var useCase: MovieUseCase

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @get:Rule
    val rule = TrampolineSchedulerRule()

    @get:Rule
    val instantExecutor = InstantTaskExecutorRule()

    @Before
    fun before(){
        viewModel = DetailViewModel(useCase)
    }

    @Test
    fun getMovieByID(){
        val observer = mock<Observer<Resource<MovieDetail>>>()
        val movies =  Resource.Success(DummyData.getMovieDetail())
        val flowable =  Flowable.just<Resource<MovieDetail>>(movies)
        Mockito.`when`(useCase.getMovieDetail(1)).thenReturn(flowable)

        viewModel.getMovieByID(1)
        val result = viewModel.moviesDetail.value

        assertEquals(movies, result)
        viewModel.moviesDetail.observeForever(observer)
        verify(observer).onChanged(movies)
        viewModel.moviesDetail.removeObserver(observer)
    }

    @Test
    fun getFavMMovies(){
        val observer = mock<Observer<Resource<List<Movie>>>>()
        val movies =  Resource.Success(DummyData.getMovies())
        val flowable =  Flowable.just<Resource<List<Movie>>>(movies)
        Mockito.`when`(useCase.getFavMovies()).thenReturn(flowable)

        viewModel.getFavMMovies()
        val result = viewModel.favMovies.value

        assertEquals(movies, result)
        viewModel.favMovies.observeForever(observer)
        verify(observer).onChanged(movies)
        viewModel.favMovies.removeObserver(observer)
    }

    @Test
    fun insertFavMovie(){
        val movie = DummyData.getMovies().first()
        viewModel.insertFavMovie(movie.id)
        verify(useCase).insertFavMovieID(movie.id)
    }

    @Test
    fun deleteFavMovie(){
        val movie = DummyData.getMovies().first()
        viewModel.deleteFavMovie(movie.id)
        verify(useCase).deleteFavMovieID(movie.id)
    }

}