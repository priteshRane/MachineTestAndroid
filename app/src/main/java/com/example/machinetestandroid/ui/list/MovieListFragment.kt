package com.example.machinetestandroid.ui.list

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.machinetestandroid.R
import com.example.machinetestandroid.data.network.MyApiService
import com.example.machinetestandroid.data.network.NetworkConnectionInterceptor
import com.example.machinetestandroid.data.network.responses.Movie
import com.example.machinetestandroid.data.repositories.MovieRepository
import com.example.machinetestandroid.databinding.MovieListFragmentBinding
import kotlinx.android.synthetic.main.movie_list_fragment.*
import kotlinx.android.synthetic.main.movie_load_state_footer.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MovieListFragment : Fragment(), MovieListItemClickListener {

    private lateinit var viewModelFactory: MovieListViewModelFactory
    private lateinit var viewModel: MovieListViewModel
    private lateinit var binding: MovieListFragmentBinding
    val movieListAdapter: MovieListAdapter = MovieListAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val networkConnectionInterceptor = NetworkConnectionInterceptor(requireContext())
        val myApiService = MyApiService(networkConnectionInterceptor)
        val movieRepository = MovieRepository(myApiService)

        viewModelFactory = MovieListViewModelFactory(movieRepository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MovieListViewModel::class.java)
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