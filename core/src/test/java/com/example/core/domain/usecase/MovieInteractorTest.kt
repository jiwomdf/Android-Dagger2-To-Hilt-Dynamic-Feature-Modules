package com.example.core.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.core.data.Resource
import com.example.core.data.repository.MovieRepository
import com.example.core.domain.model.Movie
import com.example.core.domain.model.MovieDetail
import com.example.core.util.DummyData
import com.example.favmovie.util.TrampolineSchedulerRule
import com.nhaarman.mockitokotlin2.verify
import io.reactivex.Flowable
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

class MovieInteractorTest {

    private lateinit var useCase: MovieInteractor

    @Mock
    private lateinit var repository: MovieRepository

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @get:Rule
    val rule = TrampolineSchedulerRule()

    @get:Rule
    val instantExecutor = InstantTaskExecutorRule()


    @Before
    fun setUp() {
        useCase = MovieInteractor(repository)
    }

    @Test
    fun getMovies(){
        val movies =  Resource.Success(DummyData.getMovies())
        val flowable =  Flowable.just<Resource<List<Movie>>>(movies)
        Mockito.`when`(repository.getMovies()).thenReturn(flowable)

        val result = useCase.getMovies().blockingFirst()

        assertEquals(movies, result)
    }

    @Test
    fun getMoviesByQuery(){
        val movies =  Resource.Success(DummyData.getMovies())
        val flowable =  Flowable.just<Resource<List<Movie>>>(movies)
        Mockito.`when`(repository.getMoviesByQuery("Harry Potter")).thenReturn(flowable)

        val result = useCase.getMoviesByQuery("Harry Potter").blockingFirst()

        assertEquals(movies, result)
    }

    @Test
    fun getMovieDetail(){
        val movies =  Resource.Success(DummyData.getMovieDetail())
        val flowable =  Flowable.just<Resource<MovieDetail>>(movies)
        Mockito.`when`(useCase.getMovieDetail(1)).thenReturn(flowable)

        val result = useCase.getMovieDetail(1).blockingFirst()

        assertEquals(movies, result)
    }

    @Test
    fun getFavMovies(){
        val movies =  Resource.Success(DummyData.getMovies())
        val flowable =  Flowable.just<Resource<List<Movie>>>(movies)
        Mockito.`when`(useCase.getFavMovies()).thenReturn(flowable)

        val result = useCase.getFavMovies().blockingFirst()

        assertEquals(movies, result)
    }

    @Test
    fun insertFavMovieID(){
        val movie = DummyData.getMovies().first()
        useCase.insertFavMovieID(movie.id)
        verify(repository).insertFavMovieID(movie.id)
    }

    @Test
    fun deleteFavMovieID(){
        val movie = DummyData.getMovies().first()
        useCase.deleteFavMovieID(movie.id)
        verify(repository).deleteFavMovieID(movie.id)
    }

}