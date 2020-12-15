package com.example.machinetestandroid.ui.recyclerview.list

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.machinetestandroid.R
import com.example.machinetestandroid.data.network.MyApiService
import com.example.machinetestandroid.data.network.NetworkConnectionInterceptor
import com.example.machinetestandroid.data.network.responses.Movie
import com.example.machinetestandroid.data.repository.MovieRepository
import com.example.machinetestandroid.databinding.ActivityRecyclerviewListBinding
import com.example.machinetestandroid.ui.recyclerview.details.RecyclerViewDetailsActivity
import com.example.machinetestandroid.ui.recyclerview.viewmodel.RecyclerViewViewModel

class RecyclerViewListActivity : AppCompatActivity(), RecyclerViewListInterface, RecyclerViewClickInterface {

    lateinit var viewModelFactory: RecyclerViewViewModelFactory
    lateinit var viewModel: RecyclerViewViewModel
    lateinit var binding: ActivityRecyclerviewListBinding
    private val movieListAdapter = RecyclerViewListAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val networkConnectionInterceptor = NetworkConnectionInterceptor(this)
        val myApiService = MyApiService(networkConnectionInterceptor)
        val movieRepository = MovieRepository(myApiService)

        viewModelFactory = RecyclerViewViewModelFactory(this, movieRepository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(RecyclerViewViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_recyclerview_list)

        viewModel.movieListInterface = this

        binding.recyclerView.also {
            it.layoutManager = LinearLayoutManager(this)
            it.setHasFixedSize(true)
        }

        viewModel.getMovieList()
        viewModel.movies.observe(this, Observer { movies ->
            binding.recyclerView.adapter = movieListAdapter.also { it.addMovies(movies) }
        })
    }

    override fun onMovieItemClick(view: View, movie: Movie) {
        val intent = Intent(this, RecyclerViewDetailsActivity::class.java).apply {
            this.putExtra("movie", movie)
        }
        startActivity(intent)
    }

    override fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        binding.progressBar.visibility = View.GONE
    }
}