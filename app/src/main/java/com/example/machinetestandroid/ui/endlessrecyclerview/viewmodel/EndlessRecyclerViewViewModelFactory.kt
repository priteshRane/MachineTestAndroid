package com.example.machinetestandroid.ui.endlessrecyclerview.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.machinetestandroid.data.repositories.MovieRepository

class EndlessRecyclerViewViewModelFactory(private val context: Context, private val movieRepository: MovieRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EndlessRecyclerViewViewModel::class.java)) {
            return EndlessRecyclerViewViewModel(context, movieRepository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}