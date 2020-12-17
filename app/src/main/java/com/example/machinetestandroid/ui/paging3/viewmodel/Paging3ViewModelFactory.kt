package com.example.machinetestandroid.ui.paging3.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.machinetestandroid.data.repositories.MovieRepository

class Paging3ViewModelFactory(private val movieRepository: MovieRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(Paging3ViewModel::class.java)) {
            return Paging3ViewModel(movieRepository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}