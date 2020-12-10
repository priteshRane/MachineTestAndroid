package com.example.machinetestandroid.ui.basic.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.example.machinetestandroid.R
import com.example.machinetestandroid.databinding.ActivityMovieDetailsBinding

class MovieDetailsActivity : AppCompatActivity() {

    lateinit var binding: ActivityMovieDetailsBinding
    val TAG = "MovieDetailsActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_details)

        if (intent != null) {
            val movieList = intent.extras?.getString("movieList")
            Log.i(TAG, movieList.toString())
            binding.tvDetails.text = "${movieList.toString()}"
        }
    }
}