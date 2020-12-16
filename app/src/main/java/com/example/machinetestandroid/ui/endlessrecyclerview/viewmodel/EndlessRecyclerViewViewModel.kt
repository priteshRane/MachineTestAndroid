package com.example.machinetestandroid.ui.endlessrecyclerview.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.machinetestandroid.data.network.responses.Movie
import com.example.machinetestandroid.data.repository.MovieRepository
import com.example.machinetestandroid.util.Coroutines
import com.example.machinetestandroid.util.NoInternetException
import com.example.machinetestandroid.util.toast

class EndlessRecyclerViewViewModel(private val context: Context, private val movieRepository: MovieRepository) : ViewModel() {
    val TAG = "ListViewModel"
    val PAGE_SIZE = 10
    var totalPages = 0
    var page = 1
    var isLoadingMovies = false
    var isLastPageMovies = false

    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>>
        get() = _movies

    fun getMovieList(): LiveData<List<Movie>> {
        Log.i(TAG, "Get Movies")
        if (movies.value.isNullOrEmpty() || !isLastPageMovies) {
            getMoviesFromRepository()
        }
        return movies
    }

    private fun getMoviesFromRepository() {
        Coroutines.main {
            try {
                val movieResponse = movieRepository.getMoviesFromApi(page, PAGE_SIZE)
                if (movieResponse.isSuccessful) {
                    totalPages = movieResponse.body()?.totalPages!!

                    _movies.value = movieResponse.body()?.movie!!

                    isLoadingMovies = false

                    if (page == totalPages) {
                        isLastPageMovies = true
                    }
                }
            } catch (e: NoInternetException) {
                Log.i(TAG, e.toString())
                context.toast("No Internet, Please check your connection")
            } catch (e: Exception) {
                Log.i(TAG, e.toString())
                context.toast("Something went wrong, Please try again!")
            }
        }
    }
}