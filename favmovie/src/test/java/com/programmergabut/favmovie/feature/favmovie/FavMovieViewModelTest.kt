package com.programmergabut.favmovie.feature.favmovie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.programmergabut.favmovie.util.TrampolineSchedulerRule
import com.programmergabut.core.data.Resource
import com.programmergabut.core.domain.model.Movie
import com.programmergabut.core.domain.usecase.MovieUseCase
import com.programmergabut.favmovie.util.DummyData
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import io.reactivex.Flowable
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

class FavMovieViewModelTest {

    private lateinit var viewModel: FavMovieViewModel

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
        viewModel = FavMovieViewModel(useCase)
    }

    @Test
    fun getFavMovies(){
        val observer = mock<Observer<Resource<List<Movie>>>>()
        val movies =  Resource.Success(DummyData.getMovies())
        val flowable =  Flowable.just<Resource<List<Movie>>>(movies)
        Mockito.`when`(useCase.getFavMovies()).thenReturn(flowable)

        viewModel.getFavMovies()
        val result = viewModel.favMovies.value

        Assert.assertEquals(movies, result)
        viewModel.favMovies.observeForever(observer)
        verify(observer).onChanged(movies)
        viewModel.favMovies.removeObserver(observer)
    }

}