package com.example.machinetestandroid.ui.paging3.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.navArgs
import com.example.machinetestandroid.R
import com.example.machinetestandroid.databinding.FragmentPaging3DetailsBinding

class Paging3DetailsFragment : Fragment() {

    lateinit var binding: FragmentPaging3DetailsBinding
    val args: Paging3DetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_paging3_details, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.tvDetails.text = "${args.movie.name.toString()}\n${args.movie.rating.toString()}\n${args.movie.duration.toString()}\n${args.movie.directors.toString()}"
    }
}