package com.example.machinetestandroid.ui.list

import android.content.Context
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import com.example.machinetestandroid.data.network.responses.MovieResponse
import com.example.machinetestandroid.data.repositories.MovieRepository
import com.example.machinetestandroid.util.Coroutines
import com.example.machinetestandroid.util.NoInternetException
import com.example.machinetestandroid.util.toast
import retrofit2.Response
import javax.inject.Inject

class MovieListViewModel @Inject constructor(
    val context: Context,
    val movieRepository: MovieRepository
) : ViewModel() {
    val TAG = "ListViewModel"
    val PAGE_SIZE = 10
    var totalPages = 0
    var page = 1
    var isLoadingMovies = false
    var isLastPageMovies = false

    var movieListInterface: MovieListInterface? = null

    fun getMovies(page: Int, pageSize: Int) {
        Coroutines.main {
            try {
                val movieResponse = movieRepository.getMovies(page, pageSize)
                if (movieResponse.isSuccessful) {
                    totalPages = movieResponse.body()?.totalPages!!

                    val movies = movieResponse.body()?.movie!!
                    movieListInterface?.addMovies(movies) // add interface

                    isLoadingMovies = false

                    if (page == totalPages) {
                        isLastPageMovies = true
                    }

                    movieListInterface?.setScrollPosition((page - 1) * 10)
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