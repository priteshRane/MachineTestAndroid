package com.example.machinetestandroid.data.repository

import com.example.machinetestandroid.data.network.MyApiService
import com.example.machinetestandroid.data.network.responses.MovieResponse
import retrofit2.Response

class MovieRepository(val myApiService: MyApiService) {

    suspend fun getMovies(page: Int, pageSize: Int): Response<MovieResponse> {
        return myApiService.api.movieResponse(page, pageSize)
    }
}