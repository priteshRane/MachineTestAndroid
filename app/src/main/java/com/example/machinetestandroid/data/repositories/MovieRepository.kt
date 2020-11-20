package com.example.machinetestandroid.data.repositories

import com.example.machinetestandroid.data.network.MyApiService
import com.example.machinetestandroid.data.network.response.MovieResponse
import retrofit2.Response
import javax.inject.Inject

class MovieRepository @Inject constructor(val myApiService: MyApiService) {

    suspend fun getMovies(page: Int, pageSize: Int): Response<MovieResponse> {
        return myApiService.api.movieResponse(page, pageSize)
    }
}