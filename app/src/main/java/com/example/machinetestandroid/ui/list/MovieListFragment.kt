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
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.machinetestandroid.R
import com.example.machinetestandroid.data.network.MyApiService
import com.example.machinetestandroid.data.network.NetworkConnectionInterceptor
import com.example.machinetestandroid.data.repositories.MovieRepository
import com.example.machinetestandroid.databinding.MovieListFragmentBinding
import kotlinx.android.synthetic.main.movie_list_fragment.*
import kotlinx.android.synthetic.main.movie_load_state_footer.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MovieListFragment : Fragment() {

    companion object {
        fun newInstance() = MovieListFragment()
    }

    private lateinit var viewModelFactory: MovieListViewModelFactory
    private lateinit var viewModel: MovieListViewModel
    private lateinit var binding: MovieListFragmentBinding
    val movieAdapter: MovieAdapter = MovieAdapter()

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
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.itemAnimator = null
        binding.recyclerView.adapter = movieAdapter.withLoadStateHeaderAndFooter(
            header = MovieLoadStateAdapter { movieAdapter.retry() },
            footer = MovieLoadStateAdapter { movieAdapter.retry() }
        )
        binding.btnRetry.setOnClickListener {
            movieAdapter.retry()
        }

// Flow is still experimental
//        lifecycleScope.launch {
//            viewModel.getMovies().collectLatest { pagingData ->
//                movieAdapter.submitData(pagingData)
//            }
//        }

        viewModel.movies.observe(viewLifecycleOwner) {
            movieAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

        movieAdapter.addLoadStateListener { loadState ->
            binding.apply {
                pb_progress.isVisible = loadState.source.refresh is LoadState.Loading
                recyclerView.isVisible = loadState.source.refresh is LoadState.NotLoading
                btnRetry.isVisible = loadState.source.refresh is LoadState.Error
                tv_retry.isVisible = loadState.source.refresh is LoadState.Error

                // empty view
                if (loadState.source.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached && movieAdapter.itemCount < 1) {
                    recyclerView.isVisible = false
                    tvNoResult.isVisible = true
                } else {
                    tvNoResult.isVisible = false
                }
            }
        }
    }
}