package com.example.machinetestandroid.ui.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
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

    companion object {
        fun newInstance() = MovieListFragment()
    }

    @Inject
    lateinit var viewModel: MovieListViewModel
    private lateinit var binding: MovieListFragmentBinding
    private lateinit var movieListAdapter: MovieListAdapter
    private val TAG = "MovieListFragment"
    private val PAGE_SIZE = 10
    private var totalPages = 0
    private var page = 1
    private var isLoadingMovies = false
    private var isLastPageMovies = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (requireActivity().application as MyApplication).appComponent.inject(this)
        binding = DataBindingUtil.inflate(inflater, R.layout.movie_list_fragment, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        movieListAdapter = MovieListAdapter(this)
        val layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.apply {
            this.layoutManager = layoutManager
            this.setHasFixedSize(true)
            this.adapter = movieListAdapter
        }

        callGetMoviesAPI()

        binding.recyclerView.addOnScrollListener(object :
            MoviePaginationScrollListener(layoutManager) {
            override fun loadMoreItems() {
                isLoadingMovies = true
                page += 1
                callGetMoviesAPI()
            }

            override val totalPageCount: Int
                get() = totalPages
            override val isLastPage: Boolean
                get() = isLastPageMovies
            override val isLoading: Boolean
                get() = isLoadingMovies

        })
    }

    override fun onMovieItemClick(view: View, movie: Movie) {
        val action = MovieListFragmentDirections.actionMovieListFragmentToMovieDetailsFragment()
        Navigation.findNavController(view).navigate(action)
    }

    fun callGetMoviesAPI() {
        Coroutines.main {
            try {
                binding.progressBar.visibility = View.VISIBLE

                val movieResponse = viewModel.getMovies(page, PAGE_SIZE)
                if (movieResponse.isSuccessful) {
                    totalPages = movieResponse.body()?.totalPages!!

                    val movies = movieResponse.body()?.movie!!
                    movieListAdapter.addMovies(movies)

                    binding.progressBar.visibility = View.INVISIBLE
                    isLoadingMovies = false

                    if (page == totalPages) {
                        isLastPageMovies = true
                    }

                    binding.recyclerView.scrollToPosition((page - 1) * 10)
                }
            } catch (e: NoInternetException) {
                Log.i(TAG, e.toString())
                requireActivity().toast("No Internet, Please check your connection")
            } catch (e: Exception) {
                Log.i(TAG, e.toString())
                requireActivity().toast("Something went wrong, Please try again!")
            }
        }
    }
}