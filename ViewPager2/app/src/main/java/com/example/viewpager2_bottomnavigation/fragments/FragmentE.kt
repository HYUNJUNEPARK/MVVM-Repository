package com.example.viewpager2_bottomnavigation.fragments

import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.viewpager2_bottomnavigation.R
import com.example.viewpager2_bottomnavigation.adapter.RecyclerAdapterE
import com.example.viewpager2_bottomnavigation.databinding.FragmentEBinding
import com.example.viewpager2_bottomnavigation.viewmodel.ViewModelE
import com.june.musicstreaming.fragment.BaseFragment

class FragmentE : BaseFragment<FragmentEBinding>(R.layout.fragment_e) {
    private val viewModelE: ViewModelE by viewModels()

    override fun initView() {
        super.initView()

        initRecyclerView()
        initViewModel()
    }

    private fun initRecyclerView() {
        val adapter = RecyclerAdapterE(viewModelE.itemList)
        binding.recyclerView.adapter = adapter
    }

    private fun initViewModel() {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModelE = viewModelE

        //observe LiveData
        val liveDataObserver: Observer<Int> = Observer { _ ->
            binding.recyclerView.adapter?.notifyDataSetChanged()
            binding.recyclerView.scrollToPosition(viewModelE.itemList.size -1)
        }
        viewModelE.currentItemListSize.observe(
            viewLifecycleOwner,
            liveDataObserver
        )
    }
}