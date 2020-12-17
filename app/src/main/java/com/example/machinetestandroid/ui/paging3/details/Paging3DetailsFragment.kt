package com.example.machinetestandroid.ui.paging3.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.machinetestandroid.R
import com.example.machinetestandroid.data.network.responses.Movie
import com.example.machinetestandroid.databinding.FragmentPaging3DetailsBinding

class Paging3DetailsFragment : Fragment() {

    lateinit var binding: FragmentPaging3DetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_paging3_details, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val args = requireArguments().getParcelable<Movie>("movie")
        if (args != null) {
            binding.tvDetails.text = "${args.name.toString()}\n${args.rating.toString()}\n${args.duration.toString()}\n${args.directors.toString()}"
        }
    }
}