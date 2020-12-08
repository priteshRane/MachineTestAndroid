package com.example.machinetestandroid.ui.list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.machinetestandroid.R
import com.example.machinetestandroid.data.network.responses.Movie
import kotlinx.android.synthetic.main.movie_card.view.*

lateinit var context: Context
class MovieAdapter(private val movieClickListener: MovieClickListener) :
    PagedListAdapter<Movie, MovieAdapter.ItemViewHolder>(
        DIFF_CALLBACK
    ) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewHolder {
        context = parent.context
        return ItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.movie_card, parent, false)
        )
    }

    override fun onBindViewHolder(
        holder: ItemViewHolder,
        position: Int
    ) {
        val movieEntity: Movie = getItem(position)!!
        Glide
            .with(context)
            .load(movieEntity.posterUrl)
            .centerCrop()
            .into(holder.moviePoster);

        holder.movieName.text = (position + 1).toString() + movieEntity.name
        holder.rating.text = movieEntity.rating.toString()
        holder.directors.text = movieEntity.directors
        holder.duration.text = movieEntity.duration
        holder.moviePoster.setOnClickListener {
            movieClickListener.onMovieClick(it, movieEntity)
        }
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val moviePoster = itemView.iv_movie_poster
        val movieName = itemView.tv_movie_name
        val rating = itemView.tv_rating
        val directors = itemView.tv_directors
        val duration = itemView.tv_duration
    }

    companion object {
        private val DIFF_CALLBACK = object :
            DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie,
                                         newItem: Movie) = oldItem._id == newItem._id

            override fun areContentsTheSame(oldItem: Movie,
                                            newItem: Movie) = oldItem == newItem
        }
    }
}