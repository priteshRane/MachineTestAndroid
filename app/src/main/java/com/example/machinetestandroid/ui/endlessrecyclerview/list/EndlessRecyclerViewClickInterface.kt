package com.example.machinetestandroid.ui.endlessrecyclerview.list

import android.view.View
import com.example.machinetestandroid.data.network.responses.Movie

interface EndlessRecyclerViewClickInterface {
    fun onMovieItemClick(view: View, movie: Movie)
}