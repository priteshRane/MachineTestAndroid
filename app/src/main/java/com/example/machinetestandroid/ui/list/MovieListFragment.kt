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
import com.example.machinetestandroid.data.network.responses.Movie
import com.example.machinetestandroid.databinding.MovieListFragmentBinding
import javax.inject.Inject

class MovieListFragment : Fragment(), MovieClickListener {

    private lateinit var binding: MovieListFragmentBinding

    @Inject
    lateinit var viewModel: MovieListViewModel
    val movieAdapter: MovieAdapter = MovieAdapter(this)

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
        binding.recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.adapter = movieAdapter

        viewModel.movieLiveData.observe(requireActivity(), Observer { movies ->
            movieAdapter.submitList(movies)
        })
    }

    override fun onMovieClick(view: View, movie: Movie) {
        val action = MovieListFragmentDirections.actionMovieListFragmentToMovieDetailFragment()
        action.setPosterUrl(movie.posterUrl.toString())
        action.setName(movie.name.toString())
        action.setRating(movie.rating.toString())
        action.setDuration(movie.duration.toString())
        action.setDirector(movie.directors.toString())
        Navigation.findNavController(view).navigate(action)
    }
}