package com.example.machinetestandroid

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.example.machinetestandroid.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    lateinit var binding: FragmentMainBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.btnGoToBasicApiCallingExample.setOnClickListener {
            val action = MainFragmentDirections.actionMainFragmentToBasicListFragment()
            Navigation.findNavController(it).navigate(action)
        }

        binding.btnGoToRecyclerview.setOnClickListener {
            val action = MainFragmentDirections.actionMainFragmentToRecyclerViewListFragment()
            Navigation.findNavController(it).navigate(action)
        }

        binding.btnGoToEndlessRecyclerview.setOnClickListener {
            val action = MainFragmentDirections.actionMainFragmentToEndlessRecyclerViewListFragment()
            Navigation.findNavController(it).navigate(action)
        }

        binding.btnGoToPaging3.setOnClickListener {
            val action = MainFragmentDirections.actionMainFragmentToPaging3ListFragment()
            Navigation.findNavController(it).navigate(action)
        }
    }
}