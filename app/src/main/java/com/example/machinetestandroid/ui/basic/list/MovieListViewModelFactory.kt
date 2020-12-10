package com.example.machinetestandroid.ui.basic.list

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.machinetestandroid.data.repository.MovieRepository
import java.lang.IllegalArgumentException

class MovieListViewModelFactory(val context: Context, val movieRepository: MovieRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieListViewModel::class.java)) {
            return MovieListViewModel(context, movieRepository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}