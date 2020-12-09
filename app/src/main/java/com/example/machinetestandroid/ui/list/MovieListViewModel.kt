package com.example.machinetestandroid.ui.list

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.machinetestandroid.data.network.response.Movie
import com.example.machinetestandroid.data.repositories.MovieRepository
import com.example.machinetestandroid.util.Coroutines
import com.example.machinetestandroid.util.NoInternetException
import com.example.machinetestandroid.util.toast
import javax.inject.Inject

class MovieListViewModel @Inject constructor(
    val context: Context,
    val movieRepository: MovieRepository
) : ViewModel() {
    val TAG = "MovieListViewModel"
    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>>
        get() = _movies

    fun getMovieList(): LiveData<List<Movie>> {
        Log.i(TAG, "Get Movies")
        if (movies.value == null) {
            getMoviesFromRepository()
        }
        return movies
    }

    private fun getMoviesFromRepository() {
        Coroutines.main {
            try {
                val movieResponse = movieRepository.getMovies(1, 10)
                if (movieResponse.isSuccessful) {
                    _movies.value = movieResponse.body()?.movie
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