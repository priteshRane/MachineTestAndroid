package com.example.machinetestandroid.data.network

import com.example.machinetestandroid.data.network.responses.AnswerResponse
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class MyApiService @Inject constructor(networkConnectionInterceptor: NetworkConnectionInterceptor) : MyApi {

    val baseUrl: String = "https://api.stackexchange.com/"

    val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(networkConnectionInterceptor)
            .build()

    val api = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MyApi::class.java)

    override suspend fun answerResponse(page: Int, pagesize: Int, order: String, sort: String, site: String): Response<AnswerResponse> {
        return api.answerResponse(page, pagesize, order, sort, site)
    }
}