package com.example.machinetestandroid.ui.recyclerview.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.machinetestandroid.R
import com.example.machinetestandroid.data.db.AppDatabase
import com.example.machinetestandroid.data.network.MyApiService
import com.example.machinetestandroid.data.network.NetworkConnectionInterceptor
import com.example.machinetestandroid.data.network.responses.Movie
import com.example.machinetestandroid.data.repositories.MovieRepository
import com.example.machinetestandroid.databinding.FragmentRecyclerViewListBinding
import com.example.machinetestandroid.ui.basic.details.BasicDetailsFragment
import com.example.machinetestandroid.ui.recyclerview.details.RecyclerViewDetailsFragment
import com.example.machinetestandroid.ui.recyclerview.viewmodel.RecyclerViewViewModel
import com.example.machinetestandroid.ui.recyclerview.viewmodel.RecyclerViewViewModelFactory
import com.example.machinetestandroid.util.Coroutines

class RecyclerViewListFragment : Fragment(), RecyclerViewListInterface, RecyclerViewClickInterface {

    lateinit var viewModelFactory: RecyclerViewViewModelFactory
    lateinit var viewModel: RecyclerViewViewModel
    lateinit var binding: FragmentRecyclerViewListBinding
    lateinit var recylerViewListAdapter: RecyclerViewListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val networkConnectionInterceptor = NetworkConnectionInterceptor(requireContext())
        val myApiService = MyApiService(networkConnectionInterceptor)
        val appDataBase = AppDatabase.getInstance(requireContext())
        val movieRepository = MovieRepository(myApiService, appDataBase)

        viewModelFactory = RecyclerViewViewModelFactory(requireContext(), movieRepository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(RecyclerViewViewModel::class.java)
        viewModel.recyclerViewListInterface = this
        recylerViewListAdapter = RecyclerViewListAdapter(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_recycler_view_list, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.recyclerView.also {
            it.layoutManager = LinearLayoutManager(requireContext())
            it.setHasFixedSize(true)
        }
        Coroutines.main {
            viewModel.movie.await().observe(viewLifecycleOwner, Observer { movies ->
                binding.recyclerView.adapter = recylerViewListAdapter.also { it.addMovies(movies) }
            })
        }
    }

    override fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        binding.progressBar.visibility = View.GONE
    }

    override fun onMovieItemClick(view: View, movie: Movie) {
        activity?.supportFragmentManager?.commit {
            val bundle = Bundle()
            bundle.putParcelable("movie", movie)

            replace<RecyclerViewDetailsFragment>(R.id.fragment_container_view, "RecyclerViewDetailsFragmentTag", bundle)
            setReorderingAllowed(true)
            addToBackStack("RecyclerViewDetailsFragment")
        }
    }
}