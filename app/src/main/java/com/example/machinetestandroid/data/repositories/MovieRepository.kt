package com.example.machinetestandroid.data.repositories

import com.example.machinetestandroid.data.network.MyApiService
import com.example.machinetestandroid.data.network.responses.MovieResponse
import retrofit2.Response

class MovieRepository(private val myApiService: MyApiService) {

    suspend fun getMoviesFromApi(page: Int, pageSize: Int): Response<MovieResponse> {
        return myApiService.api.movieResponse(page, pageSize)
    }
}