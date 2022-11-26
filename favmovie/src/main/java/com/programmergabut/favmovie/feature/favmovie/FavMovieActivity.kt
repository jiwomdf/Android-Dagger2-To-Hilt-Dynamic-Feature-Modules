package com.programmergabut.favmovie.feature.favmovie

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.programmergabut.movieapp.feature.main.MovieAdapter
import com.programmergabut.core.data.Resource
import com.programmergabut.favmovie.databinding.ActivityFavMovieBinding
import com.programmergabut.favmovie.di.FavMovieComponent
import com.programmergabut.favmovie.di.DaggerFavMovieComponent
import com.programmergabut.movieapp.base.BaseActivity
import com.programmergabut.movieapp.di.SubModuleDependencies
import com.programmergabut.movieapp.util.*
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject

class FavMovieActivity : BaseActivity<ActivityFavMovieBinding>() {


    private lateinit var movieAdapter: MovieAdapter
    private var component: FavMovieComponent? = null

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    val viewModel: FavMovieViewModel by viewModels { viewModelFactory }

    override fun getViewBinding(): ActivityFavMovieBinding =
        ActivityFavMovieBinding.inflate(layoutInflater)

    override fun onStart() {
        super.onStart()
        viewModel.getFavMovies()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        getActivityComponent()?.inject(this)
        super.onCreate(savedInstanceState)
        setTransparentStatusBar(binding.root)
        setAdapter()
        setListener()
    }

    private fun getActivityComponent(): FavMovieComponent? {
        if (component == null) {
            component = DaggerFavMovieComponent.builder()
                .context(applicationContext)
                .dependencies(
                    EntryPointAccessors.fromApplication(
                        applicationContext,
                        SubModuleDependencies::class.java
                    )
                )
                .build()
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