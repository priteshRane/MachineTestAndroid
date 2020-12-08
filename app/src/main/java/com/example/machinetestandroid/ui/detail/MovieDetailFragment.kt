package com.example.machinetestandroid.ui.detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.navArgs
import com.example.machinetestandroid.MyApplication
import com.example.machinetestandroid.R
import com.example.machinetestandroid.databinding.MovieDetailFragmentBinding
import javax.inject.Inject

class MovieDetailFragment : Fragment() {

    @Inject
    lateinit var viewModel: MovieDetailViewModel
    private lateinit var binding: MovieDetailFragmentBinding
    val args: MovieDetailFragmentArgs by navArgs()
    val TAG = "MovieDetailFragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity().application as MyApplication).appComponent.inject(this)
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
        viewModel = ViewModelProvider(this).get(MovieDetailViewModel::class.java)
        Log.i(TAG, args.posterUrl)
        Log.i(TAG, args.name)
        Log.i(TAG, args.rating)
        Log.i(TAG, args.duration)
        Log.i(TAG, args.director)

        binding.tvDetails.text = "${args.name}\n${args.rating}\n${args.duration}\n${args.director}"
    }
}