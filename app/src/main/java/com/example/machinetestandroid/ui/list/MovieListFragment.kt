package com.example.machinetestandroid.ui.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
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
    val TAG = "MovieListFragment"
    private val PAGE_START_BLOG = 0
    private val isLoadingBlog = false
    private val isLastPageBlog = false
    private val TOTAL_PAGES_BLOG = 0
    private val currentPageBlog = PAGE_START_BLOG

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
        binding.recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        binding.recyclerView.setHasFixedSize(true)

        Coroutines.main {
            try {
                binding.progressBar.visibility = View.VISIBLE
                val movieResponse = viewModel.getMovies(1, 10)
                if (movieResponse.isSuccessful) {
                    val movies = movieResponse.body()?.movie!!
                    binding.recyclerView.adapter = MovieListAdapter(movies, this)
                    binding.progressBar.visibility = View.INVISIBLE
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

    override fun onMovieItemClick(view: View, movie: Movie) {
        val action = MovieListFragmentDirections.actionMovieListFragmentToMovieDetailsFragment()
        Navigation.findNavController(view).navigate(action)
    }
}