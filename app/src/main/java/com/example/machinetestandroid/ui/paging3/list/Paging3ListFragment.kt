package com.example.machinetestandroid.ui.paging3.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.lifecycle.ViewModelProvider
import androidx.paging.LoadState
import com.example.machinetestandroid.R
import com.example.machinetestandroid.data.db.AppDatabase
import com.example.machinetestandroid.data.network.MyApiService
import com.example.machinetestandroid.data.network.NetworkConnectionInterceptor
import com.example.machinetestandroid.data.network.responses.Movie
import com.example.machinetestandroid.data.repositories.MovieRepository
import com.example.machinetestandroid.databinding.FragmentPaging3ListBinding
import com.example.machinetestandroid.ui.basic.details.BasicDetailsFragment
import com.example.machinetestandroid.ui.paging3.details.Paging3DetailsFragment
import com.example.machinetestandroid.ui.paging3.viewmodel.Paging3ViewModel
import com.example.machinetestandroid.ui.paging3.viewmodel.Paging3ViewModelFactory

class Paging3ListFragment : Fragment(), Paging3ListItemClickListener {

    lateinit var viewModelFactory: Paging3ViewModelFactory
    lateinit var viewModel: Paging3ViewModel
    lateinit var binding: FragmentPaging3ListBinding
    private val paging3ListAdapter = Paging3ListAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val networkConnectionInterceptor = NetworkConnectionInterceptor(requireContext())
        val myApiService = MyApiService(networkConnectionInterceptor)
        val appDataBase = AppDatabase.getInstance(requireContext())
        val movieRepository = MovieRepository(myApiService, appDataBase)

        viewModelFactory = Paging3ViewModelFactory(movieRepository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(Paging3ViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_paging3_list, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.rvMovie.apply {
            this.setHasFixedSize(true)
            this.itemAnimator = null
            this.adapter = paging3ListAdapter.withLoadStateHeaderAndFooter(
                header = Paging3LoadStateAdapter { paging3ListAdapter.retry() },
                footer = Paging3LoadStateAdapter { paging3ListAdapter.retry() }
            )
        }

        binding.btnRetry.setOnClickListener {
            paging3ListAdapter.retry()
        }

        viewModel.movies.observe(viewLifecycleOwner) {
            paging3ListAdapter.submitData(this.lifecycle, it)
        }

        paging3ListAdapter.addLoadStateListener { loadState ->
            binding.apply {
                pbProgress.isVisible = loadState.source.refresh is LoadState.Loading
                rvMovie.isVisible = loadState.source.refresh is LoadState.NotLoading
                btnRetry.isVisible = loadState.source.refresh is LoadState.Error
                tvRetry.isVisible = loadState.source.refresh is LoadState.Error

                if (loadState.source.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached && paging3ListAdapter.itemCount < 1) {
                    rvMovie.isVisible = false
                    tvNoResult.isVisible = true
                } else {
                    tvNoResult.isVisible = false
                }
            }
        }
    }

    override fun onMovieClick(view: View, movie: Movie) {
        activity?.supportFragmentManager?.commit {
            val bundle = Bundle()
            bundle.putParcelable("movie", movie)

            add<Paging3DetailsFragment>(R.id.fragment_container_view, "Paging3DetailsFragmentTag", bundle)
            setReorderingAllowed(true)
            addToBackStack("Paging3DetailsFragment")
        }
    }
}