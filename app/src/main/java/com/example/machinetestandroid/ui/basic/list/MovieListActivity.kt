package com.example.machinetestandroid.ui.basic.list

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.machinetestandroid.R
import com.example.machinetestandroid.data.network.MyApiService
import com.example.machinetestandroid.data.network.NetworkConnectionInterceptor
import com.example.machinetestandroid.data.repository.MovieRepository
import com.example.machinetestandroid.databinding.ActivityMovieListBinding
import com.example.machinetestandroid.ui.basic.details.MovieDetailsActivity

class MovieListActivity : AppCompatActivity(), MovieListInterface {

    lateinit var viewModelFactory: MovieListViewModelFactory
    lateinit var viewModel: MovieListViewModel
    lateinit var binding: ActivityMovieListBinding
    val TAG = "MovieListActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val networkConnectionInterceptor = NetworkConnectionInterceptor(this)
        val myApiService = MyApiService(networkConnectionInterceptor)
        val movieRepository = MovieRepository(myApiService)

        viewModelFactory = MovieListViewModelFactory(this, movieRepository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MovieListViewModel::class.java)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_list)
        viewModel.movieListInterface = this

        binding.btnGoToDetails.setOnClickListener {
            val movieList: String = viewModel.movies.value?.get(0)?.name.toString()
            val intent = Intent(this, MovieDetailsActivity::class.java).apply {
                this.putExtra("movieList", movieList)
            }
            startActivity(intent)
        }

        if (!viewModel.movies.value.isNullOrEmpty()) {
            showView()
        } else {
            viewModel.movies.observe(this, Observer { movies ->
                Log.i(TAG, movies.toString())
                showView()
            })

            viewModel.getMovieList()
        }
    }

    override fun showProgressBar() {
        binding.pbProgress.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        binding.pbProgress.visibility = View.GONE
    }

    private fun showView() {
        binding.btnGoToDetails.isEnabled = true
        binding.tvBestMovie.text = "The best movie is\n${viewModel.movies.value?.get(0)?.name}"
    }
}