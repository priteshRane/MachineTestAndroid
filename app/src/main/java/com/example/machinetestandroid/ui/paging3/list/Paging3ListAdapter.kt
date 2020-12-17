package com.example.machinetestandroid.ui.paging3.list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.machinetestandroid.R
import com.example.machinetestandroid.data.network.responses.Movie
import kotlinx.android.synthetic.main.paging3_recyclerview_item.view.*

lateinit var context: Context
class Paging3ListAdapter(private val paging3ListItemClickListener: Paging3ListItemClickListener) :
    PagingDataAdapter<Movie, Paging3ListAdapter.ItemViewHolder>(
        DIFF_CALLBACK
    ) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewHolder {
        context = parent.context
        return ItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.paging3_recyclerview_item, parent, false)
        )
    }

    override fun onBindViewHolder(
        holder: ItemViewHolder,
        position: Int
    ) {
        val currentItem = getItem(position)

        if (currentItem != null) {
            val movieEntity: Movie = currentItem
            Glide
                .with(context)
                .load(movieEntity.posterUrl)
                .centerCrop()
                .into(holder.moviePoster);

            holder.movieName.text = movieEntity.name
            holder.rating.text = movieEntity.rating.toString()
            holder.directors.text = movieEntity.directors
            holder.duration.text = movieEntity.duration

            holder.moviePoster.setOnClickListener {
                paging3ListItemClickListener.onMovieClick(it, movieEntity)
            }
        }
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val moviePoster: ImageView = itemView.iv_movie_poster
        val movieName: TextView = itemView.tv_movie_name
        val rating: TextView = itemView.tv_rating
        val directors: TextView = itemView.tv_directors
        val duration: TextView = itemView.tv_duration
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