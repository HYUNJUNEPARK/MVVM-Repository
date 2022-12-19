package com.example.viewpager2_bottomnavigation.fragments

import androidx.fragment.app.viewModels
import com.example.viewpager2_bottomnavigation.R
import com.example.viewpager2_bottomnavigation.databinding.FragmentABinding
import com.example.viewpager2_bottomnavigation.viewmodel.ViewModelA
import com.june.musicstreaming.fragment.BaseFragment

class FragmentA : BaseFragment<FragmentABinding>(R.layout.fragment_a) {
    private val viewModelA: ViewModelA by viewModels()

    override fun initView() {
        super.initView()

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModelA = viewModelA

        initComponents()
    }

//ViewModel 이 관리하고 있지 않음(Fragment Lifecycle 확인용)
    private fun initComponents() {
        var currentValue = binding.checkTextView.text.toString().toInt()
        binding.checkPlusButton.setOnClickListener {
            currentValue += 1
            binding.checkTextView.text = currentValue.toString()
        }
        binding.checkMinusButton.setOnClickListener {
            currentValue -= 1
            binding.checkTextView.text = currentValue.toString()
        }
    }
}