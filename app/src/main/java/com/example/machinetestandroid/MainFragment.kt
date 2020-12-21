package com.example.machinetestandroid

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.machinetestandroid.databinding.FragmentMainBinding
import com.example.machinetestandroid.ui.basic.details.BasicDetailsFragment
import com.example.machinetestandroid.ui.basic.list.BasicListFragment
import com.example.machinetestandroid.ui.endlessrecyclerview.list.EndlessRecyclerViewListFragment
import com.example.machinetestandroid.ui.paging3.list.Paging3ListFragment
import com.example.machinetestandroid.ui.recyclerview.list.RecyclerViewListFragment

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
            parentFragmentManager.commit {
                add<BasicListFragment>(R.id.fragment_container_view)
                setReorderingAllowed(true)
                addToBackStack("BasicListFragment")
            }
        }

        binding.btnGoToRecyclerview.setOnClickListener {
            parentFragmentManager.commit {
                add<RecyclerViewListFragment>(R.id.fragment_container_view)
                setReorderingAllowed(true)
                addToBackStack("RecyclerViewListFragment")
            }
        }

        binding.btnGoToEndlessRecyclerview.setOnClickListener {
            parentFragmentManager.commit {
                add<EndlessRecyclerViewListFragment>(R.id.fragment_container_view)
                setReorderingAllowed(true)
                addToBackStack("EndlessRecyclerViewListFragment")
            }
        }

        binding.btnGoToPaging3.setOnClickListener {
            parentFragmentManager.commit {
                add<Paging3ListFragment>(R.id.fragment_container_view)
                setReorderingAllowed(true)
                addToBackStack("Paging3ListFragment")
            }
        }
    }
}