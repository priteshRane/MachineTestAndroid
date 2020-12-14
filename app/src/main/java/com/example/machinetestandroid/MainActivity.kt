package com.example.machinetestandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.machinetestandroid.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.btnGoToBasicExample.setOnClickListener {
            val intent = Intent(this, com.example.machinetestandroid.ui.basic.list.MovieListActivity::class.java)
            startActivity(intent)
        }

        binding.btnGoToRecyclerview.setOnClickListener {
            val intent = Intent(this, com.example.machinetestandroid.ui.recyclerview.list.RecyclerViewListActivity::class.java)
            startActivity(intent)
        }
    }
}