package com.example.machinetestandroid.ui.list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.machinetestandroid.data.network.responses.Movie
import com.example.machinetestandroid.data.repositories.MovieRepository
import com.example.machinetestandroid.util.Coroutines
import com.example.machinetestandroid.util.NoInternetException
import kotlinx.coroutines.flow.Flow

class MovieListViewModel(val movieRepository: MovieRepository) : ViewModel() {
    val TAG = "MovieListViewModel"

    fun getMovies(): Flow<PagingData<Movie>> {
        val newResult: Flow<PagingData<Movie>> = movieRepository.getMovies()
            .cachedIn(viewModelScope)
        return newResult
    }
}