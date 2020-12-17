package com.example.machinetestandroid.ui.basic.list

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.os.persistableBundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.machinetestandroid.R
import com.example.machinetestandroid.data.db.AppDatabase
import com.example.machinetestandroid.data.network.MyApiService
import com.example.machinetestandroid.data.network.NetworkConnectionInterceptor
import com.example.machinetestandroid.data.repositories.MovieRepository
import com.example.machinetestandroid.databinding.FragmentBasicListBinding
import com.example.machinetestandroid.ui.basic.details.BasicDetailsFragment
import com.example.machinetestandroid.ui.basic.viewmodel.BasicViewModel
import com.example.machinetestandroid.ui.basic.viewmodel.BasicViewModelFactory
import com.example.machinetestandroid.ui.recyclerview.list.RecyclerViewListFragment

class BasicListFragment : Fragment(), BasicListInterface {

    lateinit var viewModelFactory: BasicViewModelFactory
    lateinit var viewModel: BasicViewModel
    lateinit var binding: FragmentBasicListBinding
    val TAG = "ListFragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val networkConnectionInterceptor = NetworkConnectionInterceptor(requireContext())
        val myApiService = MyApiService(networkConnectionInterceptor)
        val appDataBase = AppDatabase.getInstance(requireContext())
        val movieRepository = MovieRepository(myApiService, appDataBase)

        viewModelFactory = BasicViewModelFactory(requireContext(), movieRepository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(BasicViewModel::class.java)
        viewModel.basicListInterface = this
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_basic_list, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.btnGoToDetails.setOnClickListener {
            activity?.supportFragmentManager?.commit {
                val bundle = Bundle()
                bundle.putParcelable("movie", viewModel.movies.value?.get(0)!!)

                replace<BasicDetailsFragment>(R.id.fragment_container_view, "BasicDetailsFragmentTag", bundle)
                setReorderingAllowed(true)
                addToBackStack("BasicDetailsFragment")
            }
        }

        if (!viewModel.movies.value.isNullOrEmpty()) {
            showView()
        } else {
            viewModel.movies.observe(viewLifecycleOwner, Observer { movies ->
                Log.i(TAG, movies.toString())
                showView()
            })

            viewModel.getMovieList()
        }
    }

    override fun showProgressBar() {
        binding.pbProgress.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        binding.pbProgress.visibility = View.GONE
    }

    private fun showView() {
        binding.btnGoToDetails.isEnabled = true
        binding.tvBestMovie.text = "The best movie is\n${viewModel.movies.value?.get(0)?.name}"
    }
}