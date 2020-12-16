package com.example.machinetestandroid.ui.paging3.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.example.machinetestandroid.R
import com.example.machinetestandroid.data.network.responses.Movie
import com.example.machinetestandroid.databinding.ActivityMovieDetailsBinding
import com.example.machinetestandroid.databinding.ActivityPaging3DetailsBinding

class Paging3DetailsActivity : AppCompatActivity() {

    lateinit var binding: ActivityPaging3DetailsBinding
    val TAG = "Paging3DetailsActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_paging3_details)

        if (intent != null) {
            val movie: Movie? = intent.extras?.getParcelable<Movie>("movie")

            binding.tvDetails.text = "${movie?.name}"
        }
    }
}