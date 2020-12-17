package com.example.machinetestandroid.ui.recyclerview.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.machinetestandroid.R
import com.example.machinetestandroid.data.network.responses.Movie
import com.example.machinetestandroid.databinding.FragmentRecyclerViewDetailsBinding

class RecyclerViewDetailsFragment : Fragment() {

    lateinit var binding: FragmentRecyclerViewDetailsBinding
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
        val args = requireArguments().getParcelable<Movie>("movie")
        if (args != null) {
            binding.tvDetails.text = "${args.name.toString()}\n${args.rating.toString()}\n${args.duration.toString()}\n${args.directors.toString()}"
        }
    }
}