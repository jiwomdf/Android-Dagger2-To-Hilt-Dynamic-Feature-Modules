package com.programmergabut.movieapp.feature.main

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.programmergabut.movieapp.App
import com.programmergabut.movieapp.R
import com.programmergabut.movieapp.databinding.FragmentHomeBinding
import com.programmergabut.movieapp.util.PackageUtil
import com.programmergabut.movieapp.util.fadeInAndOut
import com.programmergabut.movieapp.util.stopFadeInAndOut
import com.programmergabut.core.data.Resource
import com.programmergabut.core.factory.ViewModelFactory
import com.jakewharton.rxbinding4.widget.textChangeEvents
import com.programmergabut.movieapp.base.BaseFragment
import com.programmergabut.movieapp.util.showFadeLoading
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class HomeFragment: BaseFragment<FragmentHomeBinding>() {

    companion object {
        fun newInstance(): HomeFragment {
            val fragment = HomeFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            return fragment
        }
    }

    @Inject
    lateinit var factory: ViewModelFactory
    lateinit var movieAdapter: MovieAdapter
    private val viewModel: MainViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        (requireActivity().applicationContext as App).appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding = FragmentHomeBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setAdapter()
        setListener()
        viewModel.getMovies()
        (requireActivity() as MainActivity).setTitle(getString(R.string.home))
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setListener() {
        viewModel.movies.observe(viewLifecycleOwner) {
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
            viewModel.getMovies()
        }
        binding.etSearchMovies.textChangeEvents()
            .skip(1)
            .debounce(500, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                if(!it.text.isNullOrEmpty()){
                    viewModel.getMoviesByQuery(it.text.toString())
                } else {
                    viewModel.getMovies()
                }
            }
    }

    private fun setAdapter() {
        movieAdapter = MovieAdapter()
        movieAdapter.listMovie = mutableListOf()
        movieAdapter.onClick = { movie ->
            startActivity(Intent(requireContext(), Class.forName(PackageUtil.DetailMovieActivity.reflection)).also {
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
            showFadeLoading(iLoading.iLoading1.root, null, isVisible)
            showFadeLoading(iLoading.iLoading2.root, null, isVisible)
            showFadeLoading(iLoading.iLoading3.root, null, isVisible)
            showFadeLoading(iLoading.iLoading4.root, null, isVisible)
            showFadeLoading(iLoading.iLoading5.root, null, isVisible)
            showFadeLoading(iLoading.iLoading6.root, null, isVisible)
        }
    }



}