package com.example.machinetestandroid.ui.recyclerview.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.machinetestandroid.R
import com.example.machinetestandroid.data.network.MyApiService
import com.example.machinetestandroid.data.network.NetworkConnectionInterceptor
import com.example.machinetestandroid.data.network.responses.Movie
import com.example.machinetestandroid.data.repository.MovieRepository
import com.example.machinetestandroid.databinding.ActivityRecyclerviewDetailsBinding
import com.example.machinetestandroid.ui.recyclerview.list.RecyclerViewViewModelFactory
import com.example.machinetestandroid.ui.recyclerview.viewmodel.RecyclerViewViewModel

class RecyclerViewDetailsActivity : AppCompatActivity() {

    lateinit var binding: ActivityRecyclerviewDetailsBinding

    val TAG = "RecyclerViewDetails"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_recyclerview_details)

        if (intent != null) {
            val movie: Movie? = intent.extras?.getParcelable<Movie>("movie")

            binding.tvDetails.text = "${movie?.name}"
        }
    }
}