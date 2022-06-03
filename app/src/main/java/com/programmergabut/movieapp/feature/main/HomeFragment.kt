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
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class HomeFragment: Fragment() {

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
    lateinit var binding: FragmentHomeBinding

    private val viewModel: MainViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        (requireActivity().applicationContext as App).appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        setListener()
        return binding.root
    }

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
        binding.iLoading.root.isVisible = isVisible
        binding.rvMovies.isVisible = !isVisible
        if(isVisible){
            binding.iLoading.iLoading1.root.fadeInAndOut()
            binding.iLoading.iLoading2.root.fadeInAndOut()
            binding.iLoading.iLoading3.root.fadeInAndOut()
            binding.iLoading.iLoading4.root.fadeInAndOut()
            binding.iLoading.iLoading5.root.fadeInAndOut()
            binding.iLoading.iLoading6.root.fadeInAndOut()
        } else {
            binding.iLoading.iLoading1.root.stopFadeInAndOut()
            binding.iLoading.iLoading2.root.stopFadeInAndOut()
            binding.iLoading.iLoading3.root.stopFadeInAndOut()
            binding.iLoading.iLoading4.root.stopFadeInAndOut()
            binding.iLoading.iLoading5.root.stopFadeInAndOut()
            binding.iLoading.iLoading6.root.stopFadeInAndOut()
        }
    }


}