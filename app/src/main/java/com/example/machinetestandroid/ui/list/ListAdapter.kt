package com.example.machinetestandroid.ui.list

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.machinetestandroid.R
import com.example.machinetestandroid.data.network.response.Movie
import com.example.machinetestandroid.databinding.MovieItemBinding

class ListAdapter(private val movies: List<Movie>) : RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    inner class MyViewHolder(val binding: MovieItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = MovieItemBinding.inflate(LayoutInflater.from(parent.context))
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListAdapter.MyViewHolder, position: Int) {
        holder.binding.movie = movies[position]
    }

    override fun getItemCount() = movies.size
}