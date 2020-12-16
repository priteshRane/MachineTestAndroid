package com.example.machinetestandroid.ui.basic.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.navArgs
import com.example.machinetestandroid.R
import com.example.machinetestandroid.databinding.FragmentBasicDetailsBinding
import com.example.machinetestandroid.databinding.FragmentBasicListBinding
import com.example.machinetestandroid.ui.endlessrecyclerview.details.EndlessRecyclerViewDetailsFragmentArgs

class BasicDetailsFragment : Fragment() {

    lateinit var binding: FragmentBasicDetailsBinding
    val args: BasicDetailsFragmentArgs by navArgs()
    val TAG = "DetailFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_basic_details, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.tvDetails.text = "${args.movie.name.toString()}\n${args.movie.rating.toString()}\n${args.movie.duration.toString()}\n${args.movie.directors.toString()}"
    }
}