package com.example.machinetestandroid.ui.list

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.machinetestandroid.MyApplication
import com.example.machinetestandroid.R
import com.example.machinetestandroid.data.network.responses.Movie
import com.example.machinetestandroid.databinding.ListFragment2Binding
import javax.inject.Inject

class ListFragment : Fragment(), MovieClickListener {

    private lateinit var binding: ListFragment2Binding

    @Inject
    lateinit var viewModel: ListViewModel
    val movieAdapter: MovieAdapter = MovieAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (requireActivity().application as MyApplication).appComponent.inject(this)
        binding = DataBindingUtil.inflate(inflater, R.layout.list_fragment2, container, false)
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
        val action = ListFragmentDirections.actionListFragmentToDetailFragment()
        Navigation.findNavController(view).navigate(action)
    }
}