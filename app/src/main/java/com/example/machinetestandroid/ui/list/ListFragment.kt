package com.example.machinetestandroid.ui.list

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.machinetestandroid.MyApplication
import com.example.machinetestandroid.R
import com.example.machinetestandroid.data.network.response.Movie
import com.example.machinetestandroid.databinding.ListFragmentBinding
import javax.inject.Inject

class ListFragment : Fragment(), RecyclerViewClickListener {
    private lateinit var binding: ListFragmentBinding

    @Inject
    lateinit var viewModel: ListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (requireActivity().application as MyApplication).appComponent.inject(this)
        binding = DataBindingUtil.inflate(inflater, R.layout.list_fragment, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        binding.recyclerView.setHasFixedSize(true)

        viewModel.getMovies()
        viewModel.movies.observe(viewLifecycleOwner, Observer { movies ->
            binding.recyclerView.adapter = ListAdapter(movies, this)
        })
    }

    override fun onRecyclerViewItemClick(view: View, movie: Movie) {
        val action = ListFragmentDirections.actionListFragmentToDetailsFragment()
        Navigation.findNavController(view).navigate(action)
    }
}