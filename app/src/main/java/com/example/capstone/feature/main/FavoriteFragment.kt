package com.example.capstone.feature.main

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.capstone.App
import com.example.capstone.R
import com.example.capstone.databinding.FragmentFavotireBinding
import com.example.capstone.util.PackageUtil
import com.example.capstone.util.fadeInAndOut
import com.example.capstone.util.stopFadeInAndOut
import com.example.core.data.Resource
import com.example.capstone.ui.ViewModelFactory
import javax.inject.Inject

class FavoriteFragment : Fragment() {

    companion object {
        fun newInstance(): FavoriteFragment {
            val fragment = FavoriteFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            return fragment
        }
    }

    @Inject
    lateinit var factory: ViewModelFactory
    lateinit var movieAdapter: MovieAdapter
    lateinit var binding: FragmentFavotireBinding

    private val viewModel: MainViewModel by viewModels { factory }

    override fun onStart() {
        super.onStart()
        if(view != null) viewModel.getFavMMovies()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        (requireActivity().applicationContext as App).appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavotireBinding.inflate(layoutInflater)
        setListener()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setAdapter()
        setListener()
        (requireActivity() as MainActivity).setTitle(getString(R.string.favorite))
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setListener() {
        viewModel.favMovies.observe(viewLifecycleOwner) {
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
            viewModel.getFavMMovies()
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