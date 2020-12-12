package com.example.machinetestandroid.ui.list

import android.util.Log
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.machinetestandroid.data.network.responses.Movie
import com.example.machinetestandroid.data.repositories.MovieRepository
import com.example.machinetestandroid.util.Coroutines
import com.example.machinetestandroid.util.NoInternetException
import kotlinx.coroutines.flow.Flow

class MovieListViewModel(val movieRepository: MovieRepository) : ViewModel() {
    val TAG = "MovieListViewModel"

// Flow is still experimental
//    fun getMovies(): Flow<PagingData<Movie>> {
//        val newResult: Flow<PagingData<Movie>> = movieRepository.getMovies()
//            .cachedIn(viewModelScope)
//        return newResult
//    }

    private val currentQuery = MutableLiveData(DEFAULT_QUERY)

    val movies = currentQuery.switchMap { queryString ->
        movieRepository.getMovies(queryString).cachedIn(viewModelScope)
    }

    fun searchMovies(query: String) {
        currentQuery.value = query
    }

    companion object {
        private const val DEFAULT_QUERY = ""
    }
}