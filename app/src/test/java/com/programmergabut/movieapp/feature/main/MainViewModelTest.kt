package com.programmergabut.movieapp.feature.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.programmergabut.movieapp.util.DummyData
import com.programmergabut.movieapp.util.TrampolineSchedulerRule
import com.programmergabut.core.data.Resource
import com.programmergabut.core.domain.model.Movie
import com.programmergabut.core.domain.usecase.MovieUseCase
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import io.reactivex.Flowable
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule


class MainViewModelTest {

    private lateinit var viewModel: MainViewModel

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
        viewModel = MainViewModel(useCase)
    }

    @Test
    fun getMovies() {
        val observer = mock<Observer<Resource<List<Movie>>>>()
        val movies =  Resource.Success(DummyData.getMovies())
        val flowable =  Flowable.just<Resource<List<Movie>>>(movies)
        `when`(useCase.getMovies()).thenReturn(flowable)

        viewModel.getMovies()
        val result = viewModel.movies.value

        assertEquals(movies, result)
        viewModel.movies.observeForever(observer)
        verify(observer).onChanged(movies)
        viewModel.movies.removeObserver(observer)
    }

    @Test
    fun getMoviesByQuery() {
        val observer = mock<Observer<Resource<List<Movie>>>>()
        val movies =  Resource.Success(DummyData.getMovies())
        val flowable =  Flowable.just<Resource<List<Movie>>>(movies)
        `when`(useCase.getMoviesByQuery("Harry Potter")).thenReturn(flowable)

        viewModel.getMoviesByQuery("Harry Potter")
        val result = viewModel.movies.value

        assertEquals(movies, result)
        viewModel.movies.observeForever(observer)
        verify(observer).onChanged(movies)
        viewModel.movies.removeObserver(observer)
    }
}