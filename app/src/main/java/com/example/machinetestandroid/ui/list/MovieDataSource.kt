package com.example.machinetestandroid.ui.list

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.example.machinetestandroid.data.network.MyApiService
import com.example.machinetestandroid.data.network.responses.Movie
import com.example.machinetestandroid.util.Coroutines
import com.example.machinetestandroid.util.NoInternetException
import javax.inject.Inject

class MovieDataSource @Inject constructor(
    val myApiService: MyApiService
) : PageKeyedDataSource<Int, Movie>() {

    var LOG_TAG_API = "API"
    var PAGE_SIZE = 10
    var FIRST_PAGE = 1

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Movie>
    ) {
        Coroutines.main {
            try {
                val answerResponse =
                    myApiService.movieResponse(FIRST_PAGE, PAGE_SIZE).body()!!
                callback.onResult(answerResponse.movie, null, FIRST_PAGE + 1)
            } catch (e: NoInternetException) {
                Log.d(LOG_TAG_API, "NoInternetException in ItemDataSource: " + e)
            } catch (e: Exception) {
                Log.d(LOG_TAG_API, "Exception in ItemDataSource: " + e.toString())
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        Coroutines.main {
            try {
                val answerResponse = myApiService.movieResponse(params.key, PAGE_SIZE).body()!!
                if (answerResponse.page!! <= answerResponse.totalPages!!) {
                    callback.onResult(answerResponse.movie, params.key + 1)
                }
            } catch (e: NoInternetException) {
                Log.d(LOG_TAG_API, "NoInternetException in ItemDataSource: " + e)
            } catch (e: Exception) {
                Log.d(LOG_TAG_API, "Exception in ItemDataSource: " + e.toString())
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        Coroutines.main {
            try {
                val answerResponse = myApiService.movieResponse(params.key, PAGE_SIZE).body()!!
                if (params.key > 1) {
                    callback.onResult(answerResponse.movie, params.key - 1)
                }
            } catch (e: NoInternetException) {
                Log.d(LOG_TAG_API, "NoInternetException in ItemDataSource: " + e)
            } catch (e: Exception) {
                Log.d(LOG_TAG_API, "Exception in ItemDataSource: " + e.toString())
            }
        }
    }
}