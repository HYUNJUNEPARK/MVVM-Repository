package com.example.viewpager2_bottomnavigation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.viewpager2_bottomnavigation.databinding.ItemFragmentEBinding

class RecyclerAdapterE(private val itemList: MutableList<String>) : RecyclerView.Adapter<RecyclerAdapterE.MyHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val binding = ItemFragmentEBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyHolder(binding)
    }

    inner class MyHolder(private val _binding: ItemFragmentEBinding) : RecyclerView.ViewHolder(_binding.root) {
        val binding = _binding
    }

    override fun onBindViewHolder(myHolder: MyHolder, position: Int) {
        val item = itemList[position]
        myHolder.binding.number.text = item
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}