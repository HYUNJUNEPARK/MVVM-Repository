package com.example.viewpager2_bottomnavigation.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.viewpager2_bottomnavigation.activitiy.MainActivity
import com.example.viewpager2_bottomnavigation.activitiy.MainActivity.Companion.TAG
import com.example.viewpager2_bottomnavigation.adapter.RecyclerAdapterE
import com.example.viewpager2_bottomnavigation.adapter.RecyclerAdapterE.Companion.itemList
import com.example.viewpager2_bottomnavigation.databinding.FragmentCBinding
import com.example.viewpager2_bottomnavigation.databinding.FragmentEBinding

class FragmentE : Fragment() {
    private var binding: FragmentEBinding?= null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val fragmentBinding = FragmentEBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, ">>>>>onCreateView: FragmentE")

        initRecyclerView()
        initButton()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
        Log.d(MainActivity.TAG, "onDestroy: FragmentE")
    }

    private fun initRecyclerView() {
        val adapter = RecyclerAdapterE()
        binding?.recyclerView?.adapter = adapter
        val layoutManager = LinearLayoutManager(activity)
        binding?.recyclerView?.layoutManager = layoutManager
    }

    private fun initButton() {
        binding?.plusButton?.setOnClickListener {
            val _number = itemList.size + 1
            val number = _number.toString()
            itemList.add(number)
            binding?.recyclerView?.adapter?.notifyDataSetChanged()
        }
        binding?.minusButton?.setOnClickListener {
            if (itemList.isNotEmpty()) {
                itemList.removeAt(itemList.size-1)
                binding?.recyclerView?.adapter?.notifyDataSetChanged()
            }
        }
    }
}