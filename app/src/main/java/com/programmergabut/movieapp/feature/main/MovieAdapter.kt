package com.programmergabut.movieapp.feature.main

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.programmergabut.movieapp.R
import com.programmergabut.movieapp.databinding.ListMovieItemBinding
import com.programmergabut.core.domain.model.Movie
import com.programmergabut.core.utils.Constant.IMAGE_URL_PREFIX_200
import com.programmergabut.movieapp.util.takeCaption

class MovieAdapter: RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    var onClick: ((data: Movie) -> Unit?)? = null
    var listMovie = mutableListOf<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ListMovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(parent.context, binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) =
        holder.bind(listMovie[position])

    override fun getItemCount(): Int = listMovie.size

    inner class MovieViewHolder(private val context: Context,
                                private val binding: ListMovieItemBinding
                                ): RecyclerView.ViewHolder(binding.root){
        fun bind(data: Movie){

            Glide.with(context)
                .load("$IMAGE_URL_PREFIX_200${data.backdropPath}")
                .error(R.drawable.ic_broken_image_24)
                .centerInside()
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.ivMovie)

            binding.tvMovie.text = data.title
            binding.tvMovieShort.text = data.overview.takeCaption()
            binding.root.setOnClickListener {
                onClick?.invoke(data)
            }
        }

    }

}