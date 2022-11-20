package com.programmergabut.moviedetail.feature.detail

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.programmergabut.movieapp.R
import com.programmergabut.movieapp.di.SubModuleDependencies
import com.programmergabut.movieapp.util.setDashIfNullOrEmpty
import com.programmergabut.movieapp.util.showBottomSheet
import com.programmergabut.core.data.Resource
import com.programmergabut.core.utils.Constant
import com.programmergabut.moviedetail.databinding.ActivityDetailBinding
import com.programmergabut.moviedetail.di.MovieDetailComponent
import javax.inject.Inject
import com.programmergabut.movieapp.util.fadeInAndOut
import com.programmergabut.movieapp.util.stopFadeInAndOut
import com.programmergabut.moviedetail.di.DaggerMovieDetailComponent
import dagger.hilt.android.EntryPointAccessors

class DetailMovieActivity: AppCompatActivity() {

    companion object {
        const val MOVIE_ID = "movie_id"
    }

    lateinit var binding: ActivityDetailBinding
    private var component: MovieDetailComponent? = null
    private val movieID by lazy { intent?.getIntExtra(MOVIE_ID, -1) ?: -1 }
    private var isFav: Boolean = false

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: DetailViewModel by viewModels{ viewModelFactory }

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
            component = DaggerMovieDetailComponent.builder()
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

    private fun setListener() {
        with(binding){
            viewModel.moviesDetail.observe(this@DetailMovieActivity) {
                when (it) {
                    is Resource.Success -> {
                        setLoadingAnimation(isVisible = false)
                        Glide.with(this@DetailMovieActivity)
                            .load("${Constant.IMAGE_URL_PREFIX_500}${ it.data?.backdropPath }")
                            .transition(DrawableTransitionOptions.withCrossFade())
                            .into(ivMovie)
                        tvTitle.text = it.data?.title?.setDashIfNullOrEmpty()
                        tvTagLine.text = it.data?.tagline?.setDashIfNullOrEmpty()
                        tvImbdId.text = it.data?.imdbId?.setDashIfNullOrEmpty()
                        tvVoteId.text = it.data?.voteAverage.toString().setDashIfNullOrEmpty()
                        tvOverview.text = it.data?.overview?.setDashIfNullOrEmpty()
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

            viewModel.favMovies.observe(this@DetailMovieActivity){
                when (it) {
                    is Resource.Success -> {
                        setLoadingFavAnimation(isVisible = false)
                        isFav = it.data?.any { it.id == movieID } ?: false
                        if(isFav){
                            fabFavMovie.backgroundTintList =
                                AppCompatResources.getColorStateList(this@DetailMovieActivity, R.color.red_500)
                        } else {
                            fabFavMovie.backgroundTintList =
                                AppCompatResources.getColorStateList(this@DetailMovieActivity, R.color.grey_700)
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

            fabFavMovie.setOnClickListener {
                if(!isFav){
                    viewModel.insertFavMovie(movieID)
                } else {
                    viewModel.deleteFavMovie(movieID)
                }
                viewModel.getFavMMovies()
            }
        }
    }

    private fun setLoadingAnimation(isVisible: Boolean){
        with(binding){
            iLoading.root.isVisible = isVisible
            clContent.isVisible = !isVisible
            if(isVisible){
                iLoading.iLoadingFav.fadeInAndOut()
                iLoading.iLoadingTitle.fadeInAndOut()
                iLoading.iLoadingTagLine.fadeInAndOut()
                iLoading.iLoadingImdb.fadeInAndOut()
                iLoading.iLoadingVote.fadeInAndOut()
                iLoading.iLoadingDscLabel.fadeInAndOut()
                iLoading.iLoadingDsc.fadeInAndOut()
            } else {
                iLoading.iLoadingFav.stopFadeInAndOut()
                iLoading.iLoadingTitle.stopFadeInAndOut()
                iLoading.iLoadingTagLine.stopFadeInAndOut()
                iLoading.iLoadingImdb.stopFadeInAndOut()
                iLoading.iLoadingVote.stopFadeInAndOut()
                iLoading.iLoadingDscLabel.stopFadeInAndOut()
                iLoading.iLoadingDsc.stopFadeInAndOut()
            }
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