package com.example.machinetestandroid.ui.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.machinetestandroid.MyApplication
import com.example.machinetestandroid.R
import com.example.machinetestandroid.data.network.response.Movie
import com.example.machinetestandroid.databinding.MovieListFragmentBinding
import javax.inject.Inject

class MovieListFragment : Fragment(), MovieClickListener {

    @Inject
    lateinit var viewModel: MovieListViewModel
    private lateinit var binding: MovieListFragmentBinding
    private var movieListAdapter = MovieListAdapter(this)

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
        binding.recyclerView.apply {
            this.layoutManager = layoutManager
            this.setHasFixedSize(true)
            this.adapter = movieListAdapter
        }

        if (viewModel.movies.value.isNullOrEmpty()) {
            viewModel.getMovieList()
            viewModel.movies.observe(viewLifecycleOwner, Observer { movies ->
                binding.recyclerView.adapter = movieListAdapter
                movieListAdapter.addMovies(movies)
            })
        }
    }

    override fun onMovieItemClick(view: View, movie: Movie) {
        val action = MovieListFragmentDirections.actionMovieListFragmentToMovieDetailsFragment()
        action.setMovieName(movie.name.toString())
        Navigation.findNavController(view).navigate(action)
    }
}