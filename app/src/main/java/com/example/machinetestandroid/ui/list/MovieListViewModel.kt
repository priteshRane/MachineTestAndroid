package com.example.machinetestandroid.ui.list

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.example.machinetestandroid.data.repositories.MovieRepository

class MovieListViewModel @ViewModelInject constructor(private val movieRepository: MovieRepository) : ViewModel() {
    val TAG = "MovieListViewModel"

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