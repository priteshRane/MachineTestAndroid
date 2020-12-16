package com.example.machinetestandroid.data.repositories

import androidx.lifecycle.LiveData
import com.example.machinetestandroid.data.db.AppDatabase
import com.example.machinetestandroid.data.network.MyApiService
import com.example.machinetestandroid.data.network.responses.Movie
import com.example.machinetestandroid.data.network.responses.MovieResponse
import retrofit2.Response

class MovieRepository constructor(private val myApiService: MyApiService, private val appDatabase: AppDatabase) {

    lateinit var movie: LiveData<List<Movie>>

    suspend fun getMoviesFromApi(page: Int, pageSize: Int): Response<MovieResponse> {
        return myApiService.api.movieResponse(page, pageSize)
    }

    suspend fun addMoviesToDatabase(movie: List<Movie>) {
        appDatabase.movieDao().addMovie(movie)
    }

    fun getMoviesFromDatabase() : LiveData<List<Movie>> {
        return appDatabase.movieDao().getAllMovies()
    }

    suspend fun deleteMoviesFromDatabase() {
        appDatabase.movieDao().deleteAllMovies()
    }
}