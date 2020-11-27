package com.example.machinetestandroid.ui.list

import android.content.Context
import android.util.Log
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

    suspend fun getMovies(page: Int, pageSize: Int): Response<MovieResponse> {
        return movieRepository.getMovies(page, pageSize)
    }
}