package com.example.machinetestandroid.ui.paging3.list

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.paging.LoadState
import com.example.machinetestandroid.R
import com.example.machinetestandroid.data.db.AppDatabase
import com.example.machinetestandroid.data.network.MyApiService
import com.example.machinetestandroid.data.network.NetworkConnectionInterceptor
import com.example.machinetestandroid.data.network.responses.Movie
import com.example.machinetestandroid.data.repository.MovieRepository
import com.example.machinetestandroid.databinding.ActivityPaging3ListBinding
import com.example.machinetestandroid.databinding.ActivityRecyclerviewListBinding
import com.example.machinetestandroid.ui.paging3.details.Paging3DetailsActivity
import com.example.machinetestandroid.ui.paging3.viewmodel.Paging3ViewModel
import com.example.machinetestandroid.ui.paging3.viewmodel.Paging3ViewModelFactory
import com.example.machinetestandroid.ui.recyclerview.details.RecyclerViewDetailsActivity
import com.example.machinetestandroid.ui.recyclerview.list.RecyclerViewListAdapter
import com.example.machinetestandroid.ui.recyclerview.list.RecyclerViewViewModelFactory
import com.example.machinetestandroid.ui.recyclerview.viewmodel.RecyclerViewViewModel

class Paging3ListActivity : AppCompatActivity(), Paging3ListItemClickListener {

    lateinit var viewModelFactory: Paging3ViewModelFactory
    lateinit var viewModel: Paging3ViewModel
    lateinit var binding: ActivityPaging3ListBinding
    private val paging3ListAdapter = Paging3ListAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val networkConnectionInterceptor = NetworkConnectionInterceptor(this)
        val myApiService = MyApiService(networkConnectionInterceptor)
        val appDataBase = AppDatabase.getInstance(this)
        val movieRepository = MovieRepository(myApiService, appDataBase)

        viewModelFactory = Paging3ViewModelFactory(movieRepository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(Paging3ViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_paging3_list)

        binding.rvMovie.apply {
            this.setHasFixedSize(true)
            this.itemAnimator = null
            this.adapter = paging3ListAdapter.withLoadStateHeaderAndFooter(
                header = Paging3LoadStateAdapter { paging3ListAdapter.retry() },
                footer = Paging3LoadStateAdapter { paging3ListAdapter.retry() }
            )
        }

        binding.btnRetry.setOnClickListener {
            paging3ListAdapter.retry()
        }

        viewModel.movies.observe(this) {
            paging3ListAdapter.submitData(this.lifecycle, it)
        }

        paging3ListAdapter.addLoadStateListener { loadState ->
            binding.apply {
                pbProgress.isVisible = loadState.source.refresh is LoadState.Loading
                rvMovie.isVisible = loadState.source.refresh is LoadState.NotLoading
                btnRetry.isVisible = loadState.source.refresh is LoadState.Error
                tvRetry.isVisible = loadState.source.refresh is LoadState.Error

                if (loadState.source.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached && paging3ListAdapter.itemCount < 1) {
                    rvMovie.isVisible = false
                    tvNoResult.isVisible = true
                } else {
                    tvNoResult.isVisible = false
                }
            }
        }
    }

    override fun onMovieClick(view: View, movie: Movie) {
        val intent = Intent(this, Paging3DetailsActivity::class.java).apply {
            this.putExtra("movie", movie)
        }
        startActivity(intent)
    }
}