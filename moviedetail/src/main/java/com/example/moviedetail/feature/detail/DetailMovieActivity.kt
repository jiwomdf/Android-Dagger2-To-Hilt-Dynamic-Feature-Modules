package com.example.moviedetail.feature.detail

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.capstone.App
import com.example.capstone.R
import com.example.capstone.databinding.ActivityDetailBinding
import com.example.capstone.util.fadeInAndOut
import com.example.capstone.util.setDashIfNullOrEmpty
import com.example.capstone.util.showBottomSheet
import com.example.capstone.util.stopFadeInAndOut
import com.example.core.data.Resource
import com.example.capstone.ui.ViewModelFactory
import com.example.core.utils.Constant
import com.example.moviedetail.di.DaggerMovieDetailComponent
import com.example.moviedetail.di.MovieDetailComponent
import javax.inject.Inject


class DetailMovieActivity: AppCompatActivity() {

    companion object {
        const val MOVIE_ID = "movie_id"
    }

    @Inject
    lateinit var factory: ViewModelFactory
    lateinit var binding: ActivityDetailBinding
    private var component: MovieDetailComponent? = null
    private val movieID by lazy { intent?.getIntExtra(MOVIE_ID, -1) ?: -1 }
    private var isFav: Boolean = false

    private val viewModel: DetailViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        getActivityComponent()?.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setListener()
        if(movieID > -1){
            viewModel.getMovieByID(movieID)
            viewModel.getFavMMovies()
        } else {
            showBottomSheet(
                isCancelable = false,
                isFinish = true
            )
        }
    }

    private fun getActivityComponent(): MovieDetailComponent? {
        if (component == null) {
            val coreComponent = (application as App).coreComponent
            component = DaggerMovieDetailComponent.factory().create(coreComponent)
        }
        return component
    }

    private fun setListener() {
        viewModel.moviesDetail.observe(this) {
            when (it) {
                is Resource.Success -> {
                    setLoadingAnimation(isVisible = false)
                    Glide.with(this)
                        .load("${Constant.IMAGE_URL_PREFIX_500}${ it.data?.backdropPath }")
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(binding.ivMovie)
                    binding.tvTitle.text = it.data?.title?.setDashIfNullOrEmpty()
                    binding.tvTagLine.text = it.data?.tagline?.setDashIfNullOrEmpty()
                    binding.tvImbdId.text = it.data?.imdbId?.setDashIfNullOrEmpty()
                    binding.tvVoteId.text = it.data?.voteAverage.toString().setDashIfNullOrEmpty()
                    binding.tvOverview.text = it.data?.overview?.setDashIfNullOrEmpty()
                }
                is Resource.Loading -> {
                    setLoadingAnimation(isVisible = true)
                }
                is Resource.Error -> {
                    setLoadingAnimation(isVisible = false)
                    showBottomSheet(
                        isCancelable = false,
                        isFinish = true
                    )
                }
            }
        }

        viewModel.favMovies.observe(this){
            when (it) {
                is Resource.Success -> {
                    setLoadingFavAnimation(isVisible = false)
                    isFav = it.data?.any { it.id == movieID } ?: false
                    if(isFav){
                        binding.fabFavMovie.backgroundTintList =
                            AppCompatResources.getColorStateList(this, R.color.red_500)
                    } else {
                        binding.fabFavMovie.backgroundTintList =
                            AppCompatResources.getColorStateList(this, R.color.grey_700)
                    }
                }
                is Resource.Loading -> {
                    setLoadingFavAnimation(isVisible = true)
                }
                is Resource.Error -> {
                    setLoadingFavAnimation(isVisible = false)
                }
            }
        }

        binding.fabFavMovie.setOnClickListener {
            if(!isFav){
                viewModel.insertFavMovie(movieID)
            } else {
                viewModel.deleteFavMovie(movieID)
            }
            viewModel.getFavMMovies()
        }
    }

    private fun setLoadingAnimation(isVisible: Boolean){
        binding.iLoading.root.isVisible = isVisible
        binding.clContent.isVisible = !isVisible
        if(isVisible){
            binding.iLoading.iLoadingFav.root.fadeInAndOut()
            binding.iLoading.iLoadingTitle.root.fadeInAndOut()
            binding.iLoading.iLoadingTagLine.root.fadeInAndOut()
            binding.iLoading.iLoadingImdb.root.fadeInAndOut()
            binding.iLoading.iLoadingVote.root.fadeInAndOut()
            binding.iLoading.iLoadingDscLabel.root.fadeInAndOut()
            binding.iLoading.iLoadingDsc.root.fadeInAndOut()
        } else {
            binding.iLoading.iLoadingFav.root.stopFadeInAndOut()
            binding.iLoading.iLoadingTitle.root.stopFadeInAndOut()
            binding.iLoading.iLoadingTagLine.root.stopFadeInAndOut()
            binding.iLoading.iLoadingImdb.root.stopFadeInAndOut()
            binding.iLoading.iLoadingVote.root.stopFadeInAndOut()
            binding.iLoading.iLoadingDscLabel.root.stopFadeInAndOut()
            binding.iLoading.iLoadingDsc.root.stopFadeInAndOut()
        }
    }

    private fun setLoadingFavAnimation(isVisible: Boolean){
        if(isVisible){
            binding.iLoading.iLoadingFav.root.fadeInAndOut()
        } else {
            binding.iLoading.iLoadingFav.root.stopFadeInAndOut()
        }
    }

}