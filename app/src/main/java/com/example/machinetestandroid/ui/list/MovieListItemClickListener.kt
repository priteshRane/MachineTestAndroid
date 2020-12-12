package com.example.machinetestandroid.ui.list

import android.view.View
import com.example.machinetestandroid.data.network.responses.Movie

interface MovieListItemClickListener {
    fun onMovieClick(view: View, movie: Movie)
}