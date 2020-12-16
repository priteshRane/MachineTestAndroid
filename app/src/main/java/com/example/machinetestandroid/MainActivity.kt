package com.example.machinetestandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.machinetestandroid.databinding.ActivityMainBinding
import com.example.machinetestandroid.ui.basic.list.MovieListActivity
import com.example.machinetestandroid.ui.endlessrecyclerview.list.EndlessRecyclerViewListActivity
import com.example.machinetestandroid.ui.recyclerview.list.RecyclerViewListActivity

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.btnGoToBasicExample.setOnClickListener {
            val intent = Intent(this, MovieListActivity::class.java)
            startActivity(intent)
        }

        binding.btnGoToRecyclerview.setOnClickListener {
            val intent = Intent(this, RecyclerViewListActivity::class.java)
            startActivity(intent)
        }

        binding.btnGoToEndlessRecyclerview.setOnClickListener {
            val intent = Intent(this, EndlessRecyclerViewListActivity::class.java)
            startActivity(intent)
        }
    }
}