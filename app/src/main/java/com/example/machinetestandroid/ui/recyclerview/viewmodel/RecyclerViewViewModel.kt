package com.example.machinetestandroid.ui.recyclerview.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.machinetestandroid.data.network.responses.Movie
import com.example.machinetestandroid.data.repositories.MovieRepository
import com.example.machinetestandroid.ui.recyclerview.list.RecyclerViewClickInterface
import com.example.machinetestandroid.ui.recyclerview.list.RecyclerViewListInterface
import com.example.machinetestandroid.util.Coroutines
import com.example.machinetestandroid.util.NoInternetException
import com.example.machinetestandroid.util.lazyDeferred
import com.example.machinetestandroid.util.toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RecyclerViewViewModel constructor(
    private val context: Context,
    private val movieRepository: MovieRepository
) : ViewModel() {
    val TAG = "RecyclerViewViewModel"
    var recyclerViewListInterface: RecyclerViewListInterface? = null
    private val _movies = MutableLiveData<List<Movie>>()
    val movie by lazyDeferred {
        getMovieList()
    }

    init {
        _movies.observeForever {
            addMovies(it)
        }
    }

    private suspend fun getMovieList(): LiveData<List<Movie>> {
        return withContext(Dispatchers.IO) {
            deleteMovies()
            getMoviesFromRepository()
            movieRepository.getMoviesFromDatabase()
        }
    }

    private fun getMoviesFromRepository() {
        Coroutines.main {
            try {
                recyclerViewListInterface?.showProgressBar()
                val movieResponse = movieRepository.getMoviesFromApi(1, 10)
                if (movieResponse.isSuccessful) {
                    _movies.postValue(movieResponse.body()?.movie)
                }
                recyclerViewListInterface?.hideProgressBar()
            } catch (e: NoInternetException) {
                Log.i(TAG, e.toString())
                context.toast("No Internet, Please check your connection")
            } catch (e: Exception) {
                Log.i(TAG, e.toString())
                context.toast("Something went wrong, Please try again!")
            }
        }
    }

    private fun addMovies(movie: List<Movie>) {
        Coroutines.io {
            movieRepository.addMoviesToDatabase(movie)
        }
    }

    fun deleteMovies() {
        Coroutines.io {
            movieRepository.deleteMoviesFromDatabase()
        }
    }
}