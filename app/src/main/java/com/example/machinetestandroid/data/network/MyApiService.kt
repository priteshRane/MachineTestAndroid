package com.example.machinetestandroid.data.network

import com.example.machinetestandroid.data.network.responses.MovieResponse
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class MyApiService @Inject constructor(val networkConnectionInterceptor: NetworkConnectionInterceptor) : MyApi {
    val baseUrl: String = "https://asia-south1-testapis-286008.cloudfunctions.net/movieTestApis/movie/"

    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(networkConnectionInterceptor)
        .build()

    val api = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(MyApi::class.java)

    override suspend fun movieResponse(
        page: Int,
        pageSize: Int
    ): retrofit2.Response<MovieResponse> {
        return api.movieResponse(page, pageSize)
    }
}