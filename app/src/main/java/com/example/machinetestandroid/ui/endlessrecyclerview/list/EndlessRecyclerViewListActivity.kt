package com.example.machinetestandroid.ui.endlessrecyclerview.list

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.machinetestandroid.R
import com.example.machinetestandroid.data.db.AppDatabase
import com.example.machinetestandroid.data.network.MyApiService
import com.example.machinetestandroid.data.network.NetworkConnectionInterceptor
import com.example.machinetestandroid.data.network.responses.Movie
import com.example.machinetestandroid.data.repository.MovieRepository
import com.example.machinetestandroid.databinding.ActivityEndlessRecyclerViewDetailsBinding
import com.example.machinetestandroid.databinding.ActivityEndlessRecyclerViewListBinding
import com.example.machinetestandroid.ui.endlessrecyclerview.details.EndlessRecyclerViewDetailsActivity
import com.example.machinetestandroid.ui.endlessrecyclerview.viewmodel.EndlessRecyclerViewViewModel
import com.example.machinetestandroid.ui.endlessrecyclerview.viewmodel.EndlessRecyclerViewViewModelFactory
import com.example.machinetestandroid.ui.recyclerview.details.RecyclerViewDetailsActivity
import com.example.machinetestandroid.ui.recyclerview.list.RecyclerViewViewModelFactory
import com.example.machinetestandroid.ui.recyclerview.viewmodel.RecyclerViewViewModel

class EndlessRecyclerViewListActivity : AppCompatActivity(), EndlessRecyclerViewClickInterface {

    lateinit var viewModelFactory: EndlessRecyclerViewViewModelFactory
    lateinit var viewModel: EndlessRecyclerViewViewModel
    lateinit var binding: ActivityEndlessRecyclerViewListBinding
    private val TAG = "MovieListFragment"
    val endlessRecyclerViewListAdapter = EndlessRecyclerViewListAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val networkConnectionInterceptor = NetworkConnectionInterceptor(this)
        val myApiService = MyApiService(networkConnectionInterceptor)
        val appDataBase = AppDatabase.getInstance(this)
        val movieRepository = MovieRepository(myApiService, appDataBase)

        viewModelFactory = EndlessRecyclerViewViewModelFactory(this, movieRepository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(EndlessRecyclerViewViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_endless_recycler_view_list)

        val layoutManager = LinearLayoutManager(this)

        binding.recyclerView.also {
            it.layoutManager = layoutManager
            it.setHasFixedSize(true)
            it.adapter = endlessRecyclerViewListAdapter
        }

        viewModel.getMovieList()
        viewModel.movies.observe(this, Observer { movies ->
            endlessRecyclerViewListAdapter.notifyDataSetChanged()
            endlessRecyclerViewListAdapter.addMovies(movies)
        })

        binding.recyclerView.addOnScrollListener(object :
            EndlessRecyclerViewScrollListener(layoutManager) {
            override fun loadMoreItems() {
                viewModel.isLoadingMovies = true
                viewModel.page += 1
                viewModel.getMovieList()
            }

            override val totalPageCount: Int
                get() = viewModel.totalPages
            override val isLastPage: Boolean
                get() = viewModel.isLastPageMovies
            override val isLoading: Boolean
                get() = viewModel.isLoadingMovies

        })
    }

    override fun onMovieItemClick(view: View, movie: Movie) {
        val intent = Intent(this, EndlessRecyclerViewDetailsActivity::class.java).apply {
            this.putExtra("movie", movie)
        }
        startActivity(intent)
    }
}