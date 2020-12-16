package com.example.machinetestandroid.ui.endlessrecyclerview.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.machinetestandroid.R
import com.example.machinetestandroid.databinding.FragmentEndlessRecyclerViewDetailsBinding

class EndlessRecyclerViewDetailsFragment : Fragment() {
    lateinit var binding: FragmentEndlessRecyclerViewDetailsBinding
    val args: EndlessRecyclerViewDetailsFragmentArgs by navArgs()
    val TAG = "MovieDetailFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_endless_recycler_view_details, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.tvDetails.text = "${args.movie.name.toString()}\n${args.movie.rating.toString()}\n${args.movie.duration.toString()}\n${args.movie.directors.toString()}"
    }
}