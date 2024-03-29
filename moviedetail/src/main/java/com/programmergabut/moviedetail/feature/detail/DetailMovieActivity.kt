package com.programmergabut.moviedetail.feature.detail

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.programmergabut.movieapp.App
import com.programmergabut.movieapp.R
import com.programmergabut.core.data.Resource
import com.programmergabut.core.factory.ViewModelFactory
import com.programmergabut.core.utils.Constant
import com.programmergabut.movieapp.util.*
import com.programmergabut.moviedetail.databinding.ActivityDetailBinding
import com.programmergabut.moviedetail.di.DaggerMovieDetailComponent
import com.programmergabut.moviedetail.di.MovieDetailComponent
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
        setTransparentStatusBar(null, false)

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
            binding.iLoading.iLoadingFav.fadeInAndOut()
            binding.iLoading.iLoadingTitle.fadeInAndOut()
            binding.iLoading.iLoadingTagLine.fadeInAndOut()
            binding.iLoading.iLoadingImdb.fadeInAndOut()
            binding.iLoading.iLoadingVote.fadeInAndOut()
            binding.iLoading.iLoadingDscLabel.fadeInAndOut()
            binding.iLoading.iLoadingDsc.fadeInAndOut()
        } else {
            binding.iLoading.iLoadingFav.stopFadeInAndOut()
            binding.iLoading.iLoadingTitle.stopFadeInAndOut()
            binding.iLoading.iLoadingTagLine.stopFadeInAndOut()
            binding.iLoading.iLoadingImdb.stopFadeInAndOut()
            binding.iLoading.iLoadingVote.stopFadeInAndOut()
            binding.iLoading.iLoadingDscLabel.stopFadeInAndOut()
            binding.iLoading.iLoadingDsc.stopFadeInAndOut()
        }
    }

    private fun setLoadingFavAnimation(isVisible: Boolean){
        if(isVisible){
            binding.iLoading.iLoadingFav.fadeInAndOut()
        } else {
            binding.iLoading.iLoadingFav.stopFadeInAndOut()
        }
    }

}