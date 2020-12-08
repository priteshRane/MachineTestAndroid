package com.example.machinetestandroid.ui.list

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import com.example.machinetestandroid.data.network.MyApiService
import com.example.machinetestandroid.data.network.responses.Movie
import javax.inject.Inject


class MovieListViewModel @Inject constructor(
    val context: Context,
    val movieDataSource: MovieDataSource
) : ViewModel() {

    var movieLiveData : LiveData<PagedList<Movie>>

    init {
        val config = PagedList.Config.Builder()
            .setPageSize(10)
            .setEnablePlaceholders(false)
            .build()
        movieLiveData  = initializedPagedListBuilder(config).build()
    }

    private fun initializedPagedListBuilder(config: PagedList.Config):
            LivePagedListBuilder<Int, Movie> {

        val dataSourceFactory = object : DataSource.Factory<Int, Movie>() {
            override fun create(): DataSource<Int, Movie> {
                return movieDataSource
            }
        }
        return LivePagedListBuilder<Int, Movie>(dataSourceFactory, config)
    }
}