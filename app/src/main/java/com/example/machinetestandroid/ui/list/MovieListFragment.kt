package com.example.machinetestandroid.ui.list

import android.os.Binder
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.example.machinetestandroid.MyApplication
import com.example.machinetestandroid.R
import com.example.machinetestandroid.databinding.MovieListFragmentBinding
import javax.inject.Inject

class MovieListFragment : Fragment() {

    companion object {
        fun newInstance() = MovieListFragment()
    }

    @Inject
    lateinit var viewModel: MovieListViewModel
    private lateinit var binding: MovieListFragmentBinding

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
        viewModel.getMovies()

        binding.button.setOnClickListener {
            val action = MovieListFragmentDirections.actionMovieListFragmentToMovieDetailsFragment()
            Navigation.findNavController(it).navigate(action)
        }
    }
}