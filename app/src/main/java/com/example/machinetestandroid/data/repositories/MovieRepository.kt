package com.example.machinetestandroid.data.repositories

import com.example.machinetestandroid.data.network.MyApiService
import com.example.machinetestandroid.data.network.responses.MovieResponse
import retrofit2.Response
import javax.inject.Inject

class MovieRepository @Inject constructor(val myApiService: MyApiService) {

    suspend fun movieData(page: Int, pageSize: Int): Response<MovieResponse> {
        return myApiService.movieResponse(page, pageSize)
    }
}