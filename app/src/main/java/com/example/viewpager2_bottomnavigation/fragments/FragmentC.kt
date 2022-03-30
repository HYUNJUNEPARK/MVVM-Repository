package com.example.viewpager2_bottomnavigation.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.viewpager2_bottomnavigation.R
import com.example.viewpager2_bottomnavigation.activitiy.MainActivity
import com.example.viewpager2_bottomnavigation.activitiy.MainActivity.Companion.TAG
import com.example.viewpager2_bottomnavigation.databinding.FragmentCBinding


class FragmentC : Fragment() {
    private var binding: FragmentCBinding ? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val fragmentBinding = FragmentCBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, ">>>>>onCreateView: FragmentC")
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
        Log.d(MainActivity.TAG, "onDestroy: FragmentC")
    }

}