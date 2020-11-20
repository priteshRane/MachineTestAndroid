package com.example.machinetestandroid.ui.list

import android.view.View
import com.example.machinetestandroid.data.network.response.Movie

interface RecyclerViewClickListener {
    fun onRecyclerViewItemClick(view: View, movie: Movie)
}