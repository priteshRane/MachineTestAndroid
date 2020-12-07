package com.example.machinetestandroid.ui.list

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.machinetestandroid.MyApplication
import com.example.machinetestandroid.R
import com.example.machinetestandroid.data.network.responses.Movie
import com.example.machinetestandroid.databinding.MovieListFragmentBinding
import com.example.machinetestandroid.util.Coroutines
import com.ransoft.androidpaging.util.NoInternetException
import com.ransoft.androidpaging.util.toast
import java.lang.Exception
import java.security.acl.Owner
import javax.inject.Inject

class MovieListFragment : Fragment(), MovieListInterface {

    @Inject
    lateinit var viewModel: MovieListViewModel
    private lateinit var binding: MovieListFragmentBinding
    val TAG = "MovieListFragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity().application as MyApplication).appComponent.inject(this)
        viewModel.movieListInterface = this
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
        binding.btnGoToDetails.setOnClickListener {
            val movieList = viewModel.movies.value.toString()
            val action = MovieListFragmentDirections.actionMovieListFragmentToMovieDetailFragment().setMovieListData(movieList)
            Navigation.findNavController(it).navigate(action)
        }

        if (!viewModel.movies.value.isNullOrEmpty()) {
            showView()
        } else {
            viewModel.movies.observe(viewLifecycleOwner, Observer { movies ->
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