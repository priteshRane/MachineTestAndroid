package com.example.machinetestandroid.ui.list

import com.example.machinetestandroid.data.network.responses.Movie

interface MovieListInterface {
    fun addMovies(movies: List<Movie>)
    fun setScrollPosition(position: Int)
}