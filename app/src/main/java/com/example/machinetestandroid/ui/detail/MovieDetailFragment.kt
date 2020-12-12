package com.example.machinetestandroid.ui.detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.machinetestandroid.R
import com.example.machinetestandroid.databinding.MovieDetailFragmentBinding
import com.example.machinetestandroid.ui.list.MovieListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailFragment : Fragment() {

    private val viewModel: MovieDetailViewModel by viewModels()
    private lateinit var binding: MovieDetailFragmentBinding
    val args: MovieDetailFragmentArgs by navArgs()
    val TAG = "MovieDetailFragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.movie_detail_fragment, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.i(TAG, args.movie.name.toString())

        binding.tvDetails.text = "${args.movie.name.toString()}\n${args.movie.rating.toString()}\n${args.movie.duration.toString()}\n${args.movie.directors.toString()}"
    }
}