package com.example.machinetestandroid.ui.recyclerview.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.navArgs
import com.example.machinetestandroid.R
import com.example.machinetestandroid.databinding.FragmentRecyclerViewDetailsBinding
import com.example.machinetestandroid.ui.endlessrecyclerview.details.EndlessRecyclerViewDetailsFragmentArgs

class RecyclerViewDetailsFragment : Fragment() {

    lateinit var binding: FragmentRecyclerViewDetailsBinding
    val args: RecyclerViewDetailsFragmentArgs by navArgs()
    val TAG = "DetailFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_recycler_view_details, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.tvDetails.text = "${args.movie.name.toString()}\n${args.movie.rating.toString()}\n${args.movie.duration.toString()}\n${args.movie.directors.toString()}"
    }
}