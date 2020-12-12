package com.example.machinetestandroid.ui.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.paging.LoadState
import com.example.machinetestandroid.R
import com.example.machinetestandroid.data.network.MyApiService
import com.example.machinetestandroid.data.network.NetworkConnectionInterceptor
import com.example.machinetestandroid.data.network.responses.Movie
import com.example.machinetestandroid.data.repositories.MovieRepository
import com.example.machinetestandroid.databinding.MovieListFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MovieListFragment : Fragment(), MovieListItemClickListener {

    @Inject lateinit var networkConnectionInterceptor: NetworkConnectionInterceptor
    @Inject lateinit var myApiService: MyApiService
    @Inject lateinit var movieRepository: MovieRepository

    private val viewModel: MovieListViewModel by viewModels()

    private lateinit var binding: MovieListFragmentBinding
    lateinit var movieListAdapter: MovieListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movieListAdapter = MovieListAdapter(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.movie_list_fragment, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.rvMovie.apply {
            this.setHasFixedSize(true)
            this.itemAnimator = null
            this.adapter = movieListAdapter.withLoadStateHeaderAndFooter(
                header = MovieLoadStateAdapter { movieListAdapter.retry() },
                footer = MovieLoadStateAdapter { movieListAdapter.retry() }
            )
        }

        binding.btnRetry.setOnClickListener {
            movieListAdapter.retry()
        }

        viewModel.movies.observe(viewLifecycleOwner) {
            movieListAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

        movieListAdapter.addLoadStateListener { loadState ->
            binding.apply {
                pbProgress.isVisible = loadState.source.refresh is LoadState.Loading
                rvMovie.isVisible = loadState.source.refresh is LoadState.NotLoading
                btnRetry.isVisible = loadState.source.refresh is LoadState.Error
                tvRetry.isVisible = loadState.source.refresh is LoadState.Error

                if (loadState.source.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached && movieListAdapter.itemCount < 1) {
                    rvMovie.isVisible = false
                    tvNoResult.isVisible = true
                } else {
                    tvNoResult.isVisible = false
                }
            }
        }
    }

    override fun onMovieClick(view: View, movie: Movie) {
        val action = MovieListFragmentDirections.actionMovieListFragmentToMovieDetailFragment(movie)
        Navigation.findNavController(view).navigate(action)
    }
}