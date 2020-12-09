package com.example.machinetestandroid.ui.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.machinetestandroid.MyApplication
import com.example.machinetestandroid.R
import com.example.machinetestandroid.data.network.responses.Movie
import com.example.machinetestandroid.databinding.MovieListFragmentBinding
import com.example.machinetestandroid.util.Coroutines
import com.example.machinetestandroid.util.NoInternetException
import com.example.machinetestandroid.util.toast
import javax.inject.Inject


class MovieListFragment : Fragment(), MovieClickListener {

    @Inject
    lateinit var viewModel: MovieListViewModel
    private lateinit var binding: MovieListFragmentBinding
    private val movieListAdapter = MovieListAdapter(this)
    private val TAG = "MovieListFragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity().application as MyApplication).appComponent.inject(this)
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
        val layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.also {
            it.layoutManager = layoutManager
            it.setHasFixedSize(true)
        }

        viewModel.getMovieList()
        viewModel.movies.observe(viewLifecycleOwner, Observer { movies ->
            binding.recyclerView.adapter = movieListAdapter
            movieListAdapter.addMovies(movies)
        })

        binding.recyclerView.addOnScrollListener(object :
            MoviePaginationScrollListener(layoutManager) {
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
        val action = MovieListFragmentDirections.actionMovieListFragmentToMovieDetailsFragment()
        Navigation.findNavController(view).navigate(action)
    }
}