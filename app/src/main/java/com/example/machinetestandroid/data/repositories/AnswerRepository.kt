package com.example.machinetestandroid.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.machinetestandroid.data.network.MyApi
import com.example.machinetestandroid.data.network.MyApiService
import com.example.machinetestandroid.data.network.responses.AnswerResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class AnswerRepository @Inject constructor(val myApiService: MyApiService) {

    suspend fun answerData(page: Int, pageSize: Int, order: String, sort: String, site: String): Response<AnswerResponse> {
        return myApiService.answerResponse(page, pageSize, order, sort, site)
    }
}