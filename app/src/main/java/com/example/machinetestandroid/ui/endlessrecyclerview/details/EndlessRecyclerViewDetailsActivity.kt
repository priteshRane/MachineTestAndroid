package com.example.machinetestandroid.ui.endlessrecyclerview.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.machinetestandroid.R
import com.example.machinetestandroid.data.network.responses.Movie
import com.example.machinetestandroid.databinding.ActivityEndlessRecyclerViewDetailsBinding
import com.example.machinetestandroid.databinding.ActivityRecyclerviewDetailsBinding

class EndlessRecyclerViewDetailsActivity : AppCompatActivity() {
    lateinit var binding: ActivityEndlessRecyclerViewDetailsBinding

    val TAG = "RecyclerViewDetails"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_endless_recycler_view_details)

        if (intent != null) {
            val movie: Movie? = intent.extras?.getParcelable<Movie>("movie")

            binding.tvDetails.text = "${movie?.name}"
        }
    }
}