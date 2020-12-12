package com.example.machinetestandroid.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.machinetestandroid.data.network.MyApiService
import com.example.machinetestandroid.data.network.responses.Movie
import kotlinx.coroutines.flow.Flow

class MovieRepository(val myApiService: MyApiService) {
// Flow is still experimental
//    fun getMovies(): Flow<PagingData<Movie>> {
//        return Pager(
//            config = PagingConfig(
//                pageSize = 10,
//                enablePlaceholders = false
//            ),
//            pagingSourceFactory = { MovieListPagingSource(myApiService) }
//        ).flow
//    }

    fun getMovies(query: String) =
        Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { MovieListPagingSource(myApiService, query) }
        ).liveData
}