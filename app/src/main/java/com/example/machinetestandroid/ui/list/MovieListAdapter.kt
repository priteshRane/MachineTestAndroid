package com.example.machinetestandroid.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.machinetestandroid.data.network.responses.Movie
import com.example.machinetestandroid.databinding.MovieItemBinding
import java.util.ArrayList

class MovieListAdapter(private val listener: MovieClickListener) : RecyclerView.Adapter<MovieListAdapter.MyViewHolder>() {
    private var movies: MutableList<Movie> = mutableListOf()

    fun addMovies(_movies: List<Movie>) {
        movies.addAll(_movies)
    }

    inner class MyViewHolder(val binding: MovieItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = MovieItemBinding.inflate(LayoutInflater.from(parent.context))
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieListAdapter.MyViewHolder, position: Int) {
        val movie: Movie = movies[position]
        holder.binding.name.text = movie.name
        holder.binding.rating.text = movie.rating.toString()
        holder.binding.year.text = movie.year.toString()
        Glide.with(holder.binding.poster).load(movie.posterUrl).into(holder.binding.poster)

        holder.binding.poster.setOnClickListener {
            listener.onMovieItemClick(holder.binding.root, movies[position])
        }
    }

    override fun getItemCount() = movies.size
}