package com.example.machinetestandroid.ui.recyclerview.list

import android.view.View
import com.example.machinetestandroid.data.network.responses.Movie

interface RecyclerViewClickInterface {
    fun onMovieItemClick(view: View, movie: Movie)
}