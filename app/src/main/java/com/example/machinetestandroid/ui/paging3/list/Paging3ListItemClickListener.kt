package com.example.machinetestandroid.ui.paging3.list

import android.view.View
import com.example.machinetestandroid.data.network.responses.Movie

interface Paging3ListItemClickListener {

    fun onMovieClick(view: View, movie: Movie)
}