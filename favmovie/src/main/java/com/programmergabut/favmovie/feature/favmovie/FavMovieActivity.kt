package com.programmergabut.favmovie.feature.favmovie

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.programmergabut.movieapp.App
import com.programmergabut.movieapp.feature.main.MovieAdapter
import com.programmergabut.core.data.Resource
import com.programmergabut.core.factory.ViewModelFactory
import com.programmergabut.favmovie.databinding.ActivityFavMovieBinding
import com.programmergabut.favmovie.di.DaggerFavMovieComponent
import com.programmergabut.favmovie.di.FavMovieComponent
import com.programmergabut.movieapp.util.*
import javax.inject.Inject

class FavMovieActivity : AppCompatActivity() {

    @Inject
    lateinit var factory: ViewModelFactory
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var binding: ActivityFavMovieBinding
    private var component: FavMovieComponent? = null
    private val viewModel: FavMovieViewModel by viewModels { factory }

    override fun onStart() {
        super.onStart()
        viewModel.getFavMovies()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        getActivityComponent()?.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityFavMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setTransparentStatusBar(binding.root)
        setAdapter()
        setListener()
    }

    private fun getActivityComponent(): FavMovieComponent? {
        if (component == null) {
            val coreComponent = (application as App).coreComponent
            component = DaggerFavMovieComponent.factory().create(coreComponent)
        }
        return component
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setListener() {
        viewModel.favMovies.observe(this) {
            when (it) {
                is Resource.Success, is Resource.Error -> {
                    movieAdapter.listMovie = it.data?.toMutableList() ?: mutableListOf()
                    movieAdapter.notifyDataSetChanged()
                    binding.tvEmptyMovie.isVisible = (it.data?.size ?: 0) <= 0
                    setLoadingAnimation(false)
                }
                is Resource.Loading -> {
                    setLoadingAnimation(true)
                }
            }
            binding.srlContent.isRefreshing = false
        }
        binding.srlContent.setOnRefreshListener {
            viewModel.getFavMovies()
        }
    }

    private fun setAdapter() {
        movieAdapter = MovieAdapter()
        movieAdapter.listMovie = mutableListOf()
        movieAdapter.onClick = { movie ->
            startActivity(Intent(this, Class.forName(PackageUtil.DetailMovieActivity.reflection)).also {
                it.putExtra(PackageUtil.DetailMovieActivity.movie_id, movie.id)
            })
        }
        binding.rvMovies.apply {
            adapter = movieAdapter
            setHasFixedSize(true)
        }
    }

    private fun setLoadingAnimation(isVisible: Boolean){
        with(binding){
            iLoading.root.isVisible = isVisible
            rvMovies.isVisible = !isVisible
            showFadeLoading(iLoading.iLoading1, null, isVisible)
            showFadeLoading(iLoading.iLoading2, null, isVisible)
            showFadeLoading(iLoading.iLoading3, null, isVisible)
            showFadeLoading(iLoading.iLoading4, null, isVisible)
            showFadeLoading(iLoading.iLoading5, null, isVisible)
            showFadeLoading(iLoading.iLoading6, null, isVisible)
        }
    }


}