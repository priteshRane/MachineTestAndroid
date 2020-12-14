package com.example.machinetestandroid.ui.recyclerview.list

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.machinetestandroid.data.repository.MovieRepository
import com.example.machinetestandroid.ui.recyclerview.viewmodel.RecyclerViewViewModel
import java.lang.IllegalArgumentException

class RecyclerViewListViewModelFactory(private val context: Context, private val movieRepository: MovieRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecyclerViewListActivity::class.java)) {
            return RecyclerViewViewModel(context, movieRepository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}