package com.example.machinetestandroid.ui.endlessrecyclerview.list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.machinetestandroid.R
import com.example.machinetestandroid.data.network.responses.Movie


class EndlessRecyclerViewListAdapter(private val endlessRecyclerViewClickInterface: EndlessRecyclerViewClickInterface) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val ITEM = 0
    private val LOADING = 1
    private val movies = mutableListOf<Movie>()
    private val context: Context? = null
    private var isLoadingAdded = false

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvPoster = itemView.findViewById<ImageView>(R.id.iv_movie_poster)
        val tvName = itemView.findViewById<TextView>(R.id.tv_movie_name)
        val tvRating = itemView.findViewById<TextView>(R.id.tv_rating)
        val tvDuration = itemView.findViewById<TextView>(R.id.tv_duration)
        val tvDirectors = itemView.findViewById<TextView>(R.id.tv_directors)
    }

    inner class LoadingView(itemView: View?) :
        RecyclerView.ViewHolder(itemView!!)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var viewHolder: RecyclerView.ViewHolder? = null
        val inflater = LayoutInflater.from(parent.context)

        when (viewType) {
            ITEM -> viewHolder = getViewHolder(parent, inflater)
            LOADING -> {
                val v2: View = inflater.inflate(
                    R.layout.endless_recyclerview_loading,
                    parent,
                    false
                )
                viewHolder = LoadingView(v2)
            }
        }
        return viewHolder!!
    }

    private fun getViewHolder(
        parent: ViewGroup,
        inflater: LayoutInflater
    ): RecyclerView.ViewHolder {
        val viewHolder: RecyclerView.ViewHolder
        val v1: View = inflater.inflate(R.layout.endless_recyclerview_item, parent, false)
        viewHolder = MyViewHolder(v1)
        return viewHolder
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        val movie = movies.get(position)

        when (getItemViewType(position)) {
            ITEM -> {
                val blogView: MyViewHolder = holder as MyViewHolder
                Glide.with(blogView.tvPoster).load(movie.posterUrl).into(blogView.tvPoster)
                blogView.tvName.text = movie.name
                blogView.tvRating.text = movie.rating.toString()
                blogView.tvDuration.text = movie.duration
                blogView.tvDirectors.text = movie.directors

                blogView.tvPoster.setOnClickListener {
                    endlessRecyclerViewClickInterface.onMovieItemClick(it, movies[position])
                }
            }
            LOADING -> {
            }
        }
    }

    override fun getItemCount() = movies.size

    override fun getItemViewType(position: Int) = if (position == movies.size - 1 && isLoadingAdded) LOADING else ITEM

    fun addMovie(movie: Movie) {
        movies.add(movie)
        notifyItemInserted(movies.size - 1)
    }

    fun addAllMovies(_movies: List<Movie>) {
        movies.addAll(_movies)
    }

    fun removeMovie(movie: Movie) {
        val position: Int = movies.indexOf(movie)
        if (position > -1) {
            movies.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun clear() {
        isLoadingAdded = false
        while (itemCount > 0) {
            removeMovie(getMovie(0))
        }
    }

    fun isEmpty(): Boolean {
        return itemCount == 0
    }


    fun addLoadingFooter() {
        isLoadingAdded = true
        addMovie(Movie("", "", 0, 0.0, 0, "", "", "", "", "", "", "", "", "", 0))
    }

    fun removeLoadingFooter() {
        isLoadingAdded = false
        val position: Int = movies.size - 1
        val item: Movie = getMovie(position)
        if (item != null) {
            movies.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun getMovie(position: Int): Movie {
        return movies.get(position)
    }
}