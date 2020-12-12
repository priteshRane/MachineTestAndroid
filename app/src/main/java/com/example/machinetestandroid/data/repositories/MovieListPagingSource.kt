package com.example.machinetestandroid.data.repositories

import androidx.paging.PagingSource
import com.example.machinetestandroid.data.network.MyApiService
import com.example.machinetestandroid.data.network.responses.Movie
import retrofit2.HttpException
import java.io.IOException

class MovieListPagingSource(val myApiService: MyApiService, val query: String) : PagingSource<Int, Movie>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val position = params.key ?: 1
        return try {
            val response = myApiService.movieResponse(position, params.loadSize)
            val repos = response.body()?.movie
            LoadResult.Page(
                data = repos!!,
                // prevKey = if (position == 1) null else position - 1,
                prevKey = null,
                nextKey = if (repos.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}