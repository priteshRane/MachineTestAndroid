package com.example.machinetestandroid.data.network

import com.example.machinetestandroid.data.network.responses.AnswerResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MyApi {

    @GET("2.2/answers")
    suspend fun answerResponse(
            @Query("page") page: Int,
            @Query("pagesize") pagesize: Int,
            @Query("order") order: String,
            @Query("sort") sort: String,
            @Query("site") site: String
    ): Response<AnswerResponse>
}