package com.example.machinetestandroid.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.machinetestandroid.data.network.response.Movie
import com.example.machinetestandroid.databinding.MovieItemBinding

class MovieListAdapter(private val movies: List<Movie>, private val listener: MovieClickListener) : RecyclerView.Adapter<MovieListAdapter.MyViewHolder>() {

    inner class MyViewHolder(val binding: MovieItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = MovieItemBinding.inflate(LayoutInflater.from(parent.context))
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieListAdapter.MyViewHolder, position: Int) {
        holder.binding.movie = movies[position]
        holder.binding.poster.setOnClickListener {
            listener.onRecyclerViewItemClick(holder.binding.root, movies[position])
        }
    }

    override fun getItemCount() = movies.size
}