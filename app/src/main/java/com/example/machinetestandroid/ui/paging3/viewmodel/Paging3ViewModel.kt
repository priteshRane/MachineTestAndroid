package com.example.machinetestandroid.ui.paging3.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.machinetestandroid.data.repositories.MovieRepository

class Paging3ViewModel(private val movieRepository: MovieRepository) : ViewModel() {
    val TAG = "Paging3ViewModel"

    private val currentQuery = MutableLiveData(DEFAULT_QUERY)

    val movies = currentQuery.switchMap { queryString ->
        movieRepository.getMoviesFromPagingSource(queryString).cachedIn(viewModelScope)
    }

    fun searchMovies(query: String) {
        currentQuery.value = query
    }

    companion object {
        private const val DEFAULT_QUERY = ""
    }
}