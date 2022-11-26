package com.programmergabut.moviedetail.feature.detail

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.programmergabut.movieapp.R
import com.programmergabut.movieapp.di.SubModuleDependencies
import com.programmergabut.core.data.Resource
import com.programmergabut.core.utils.Constant
import com.programmergabut.movieapp.base.BaseActivity
import com.programmergabut.movieapp.util.*
import com.programmergabut.moviedetail.databinding.ActivityDetailBinding
import com.programmergabut.moviedetail.di.MovieDetailComponent
import javax.inject.Inject
import com.programmergabut.moviedetail.di.DaggerMovieDetailComponent
import dagger.hilt.android.EntryPointAccessors

class DetailMovieActivity: BaseActivity<ActivityDetailBinding>() {

    companion object {
        const val MOVIE_ID = "movie_id"
    }

    private var component: MovieDetailComponent? = null
    private val movieID by lazy { intent?.getIntExtra(MOVIE_ID, -1) ?: -1 }
    private var isFav: Boolean = false

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: DetailViewModel by viewModels{ viewModelFactory }

    override fun getViewBinding(): ActivityDetailBinding =
        ActivityDetailBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        getActivityComponent()?.inject(this)
        super.onCreate(savedInstanceState)
        setTransparentStatusBar(null, false)
        setStatusBarThemeMode(false)

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
            clContent.isVisible = !isVisible
            iLoading.root.isVisible = isVisible
            showFadeLoading(iLoading.iLoadingFav, null, isVisible)
            showFadeLoading(iLoading.iLoadingTitle, null, isVisible)
            showFadeLoading(iLoading.iLoadingTagLine, null, isVisible)
            showFadeLoading(iLoading.iLoadingImdb, null, isVisible)
            showFadeLoading(iLoading.iLoadingVote, null, isVisible)
            showFadeLoading(iLoading.iLoadingDscLabel, null, isVisible)
            showFadeLoading(iLoading.iLoadingDsc, null, isVisible)
        }
    }

    private fun setLoadingFavAnimation(isVisible: Boolean){
        showFadeLoading(binding.iLoading.iLoadingFav, null, isVisible)
    }

}