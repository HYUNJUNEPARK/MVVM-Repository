package com.example.viewpager2_bottomnavigation.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.viewpager2_bottomnavigation.activitiy.MainActivity.Companion.TAG
import com.example.viewpager2_bottomnavigation.databinding.FragmentABinding

class FragmentA : Fragment() {
    private var binding: FragmentABinding? = null
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val fragmentBinding = FragmentABinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, ">>>>>onCreateView: FragmentA")
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
        Log.d(TAG, "onDestroy: FragmentA")
    }
}