package com.example.machinetestandroid.data.repository

import com.example.machinetestandroid.data.db.AppDatabase
import com.example.machinetestandroid.data.network.MyApiService
import com.example.machinetestandroid.data.network.responses.Movie
import com.example.machinetestandroid.data.network.responses.MovieResponse
import retrofit2.Response

class MovieRepository constructor(private val myApiService: MyApiService, private val appDatabase: AppDatabase) {

    suspend fun getMoviesFromApi(page: Int, pageSize: Int): Response<MovieResponse> {
        return myApiService.api.movieResponse(page, pageSize)
    }

    suspend fun getMoviesFromApiThenDatabase(page: Int, pageSize: Int): List<Movie> {
        // TODO: Check for internet connection, If connection is not available get data from database
        val movieResponse = myApiService.api.movieResponse(page, pageSize)
        if (movieResponse.isSuccessful) {
            appDatabase.movieDao().deleteAllMovies()
            movieResponse.body()?.movie?.let { appDatabase.movieDao().addMovie(it) }
        }
        return appDatabase.movieDao().getAllMovies()
    }
}