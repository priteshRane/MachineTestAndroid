package com.example.machinetestandroid.ui.endlessrecyclerview.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.machinetestandroid.R
import com.example.machinetestandroid.data.db.AppDatabase
import com.example.machinetestandroid.data.network.MyApiService
import com.example.machinetestandroid.data.network.NetworkConnectionInterceptor
import com.example.machinetestandroid.data.network.responses.Movie
import com.example.machinetestandroid.data.repositories.MovieRepository
import com.example.machinetestandroid.databinding.FragmentEndlessRecyclerViewListBinding
import com.example.machinetestandroid.ui.endlessrecyclerview.viewmodel.EndlessRecyclerViewViewModel
import com.example.machinetestandroid.ui.endlessrecyclerview.viewmodel.EndlessRecyclerViewViewModelFactory

class EndlessRecyclerViewListFragment : Fragment(), EndlessRecyclerViewClickInterface {

    lateinit var viewModelFactory: EndlessRecyclerViewViewModelFactory
    lateinit var viewModel: EndlessRecyclerViewViewModel
    lateinit var binding: FragmentEndlessRecyclerViewListBinding
    private val TAG = "ListFragment"
    val endlessRecyclerViewListAdapter = EndlessRecyclerViewListAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val networkConnectionInterceptor = NetworkConnectionInterceptor(requireContext())
        val myApiService = MyApiService(networkConnectionInterceptor)
        val appDataBase = AppDatabase.getInstance(requireContext())
        val movieRepository = MovieRepository(myApiService, appDataBase)

        viewModelFactory = EndlessRecyclerViewViewModelFactory(requireContext(), movieRepository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(EndlessRecyclerViewViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_endless_recycler_view_list, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val layoutManager = LinearLayoutManager(requireContext())

        binding.recyclerView.also {
            it.layoutManager = layoutManager
            it.setHasFixedSize(true)
            it.adapter = endlessRecyclerViewListAdapter
        }

        viewModel.getMovieList()
        viewModel.movies.observe(viewLifecycleOwner, Observer { movies ->
            endlessRecyclerViewListAdapter.notifyDataSetChanged()
            endlessRecyclerViewListAdapter.addAllMovies(movies)
        })

        binding.recyclerView.addOnScrollListener(object :
            EndlessRecyclerViewScrollListener(layoutManager) {
            override fun loadMoreItems() {
//                Coroutines.io {
//                    delay(5000)
                viewModel.isLoadingMovies = true
                viewModel.page += 1
                viewModel.getMovieList()
//                }
            }

            override val totalPageCount: Int
                get() = viewModel.totalPages
            override val isLastPage: Boolean
                get() = viewModel.isLastPageMovies
            override val isLoading: Boolean
                get() = viewModel.isLoadingMovies

        })
    }

    override fun onMovieItemClick(view: View, movie: Movie) {
        val action = EndlessRecyclerViewListFragmentDirections.actionEndlessRecyclerViewListFragmentToEndlessRecyclerViewDetailsFragment(movie)
        Navigation.findNavController(view).navigate(action)
    }
}