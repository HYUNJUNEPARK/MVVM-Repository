package com.example.viewpager2_bottomnavigation.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.viewpager2_bottomnavigation.activitiy.MainActivity.Companion.TAG
import com.example.viewpager2_bottomnavigation.databinding.FragmentABinding
import com.example.viewpager2_bottomnavigation.viewmodel.ViewModelA

class FragmentA : Fragment() {
    private var binding: FragmentABinding? = null
    private val viewModel: ViewModelA by viewModels()
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val fragmentBinding = FragmentABinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, ">>>>>onCreateView: FragmentA")

        binding?.lifecycleOwner = viewLifecycleOwner
        binding?.viewModel = viewModel

        initComponents()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: FragmentA")
        binding = null
    }

//Fragment Lifecycle 확인용
    private fun initComponents() {
        var currentValue = binding?.checkTextView?.text.toString().toInt()
        binding?.checkPlusButton?.setOnClickListener {
            currentValue += 1
            binding?.checkTextView?.text = currentValue.toString()
        }
        binding?.checkMinusButton?.setOnClickListener {
            currentValue -= 1
            binding?.checkTextView?.text = currentValue.toString()
        }
    }
}