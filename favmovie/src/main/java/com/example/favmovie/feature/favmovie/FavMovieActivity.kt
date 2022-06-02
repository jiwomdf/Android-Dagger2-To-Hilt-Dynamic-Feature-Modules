package com.example.favmovie.feature.favmovie

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.example.capstone.feature.main.MovieAdapter
import com.example.capstone.util.PackageUtil
import com.example.core.data.Resource
import com.example.favmovie.databinding.ActivityFavMovieBinding
import com.example.favmovie.di.FavMovieComponent
import com.example.capstone.util.fadeInAndOut
import com.example.capstone.util.stopFadeInAndOut
import com.example.favmovie.di.DaggerFavMovieComponent
import com.example.capstone.di.SubModuleDependencies
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject

class FavMovieActivity : AppCompatActivity() {


    private lateinit var movieAdapter: MovieAdapter
    private lateinit var binding: ActivityFavMovieBinding
    private var component: FavMovieComponent? = null

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    val viewModel: FavMovieViewModel by viewModels { viewModelFactory }

    override fun onStart() {
        super.onStart()
        viewModel.getFavMovies()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        getActivityComponent()?.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityFavMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)
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
        binding.iLoading.root.isVisible = isVisible
        binding.rvMovies.isVisible = !isVisible
        if(isVisible){
            binding.iLoading.iLoading1.fadeInAndOut()
            binding.iLoading.iLoading2.fadeInAndOut()
            binding.iLoading.iLoading3.fadeInAndOut()
            binding.iLoading.iLoading4.fadeInAndOut()
            binding.iLoading.iLoading5.fadeInAndOut()
            binding.iLoading.iLoading6.fadeInAndOut()
        } else {
            binding.iLoading.iLoading1.stopFadeInAndOut()
            binding.iLoading.iLoading2.stopFadeInAndOut()
            binding.iLoading.iLoading3.stopFadeInAndOut()
            binding.iLoading.iLoading4.stopFadeInAndOut()
            binding.iLoading.iLoading5.stopFadeInAndOut()
            binding.iLoading.iLoading6.stopFadeInAndOut()
        }
    }

}