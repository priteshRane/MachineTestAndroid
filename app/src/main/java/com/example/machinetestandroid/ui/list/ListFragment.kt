package com.example.machinetestandroid.ui.list

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.NetworkOnMainThreadException
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.example.machinetestandroid.MyApplication
import com.example.machinetestandroid.R
import com.example.machinetestandroid.data.network.MyApiService
import com.example.machinetestandroid.data.network.NetworkConnectionInterceptor
import com.example.machinetestandroid.data.repositories.MovieRepository
import com.example.machinetestandroid.databinding.ListFragment2Binding
import javax.inject.Inject

class ListFragment : Fragment() {

    companion object {
        fun newInstance() = ListFragment()
    }

    private lateinit var binding: ListFragment2Binding

    @Inject
    lateinit var viewModel: ListViewModel

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
        binding.button.setOnClickListener {
            val action = ListFragmentDirections.actionListFragmentToDetailFragment()
            Navigation.findNavController(it).navigate(action)
        }

        viewModel.getMovies()
    }
}