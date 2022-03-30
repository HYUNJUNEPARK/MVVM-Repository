package com.example.viewpager2_bottomnavigation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.viewpager2_bottomnavigation.activitiy.MainActivity.Companion.TAG
import com.example.viewpager2_bottomnavigation.adapter.RecyclerAdapterE.Companion.itemList

class ViewModelE: ViewModel() {
    private val _currentItemListSize = MutableLiveData<Int>()
    //private val _currentItemListSize = MutableLiveData<MutableList<String>>()

    val currentItemListSize: LiveData<Int>
        get() = _currentItemListSize

    init {
        _currentItemListSize.value = itemList.size
    }

    fun addItem() {
        val _number = itemList.size + 1
        val number = _number.toString()
        itemList.add(number)
        _currentItemListSize.value = itemList.size + 1
    }

    fun subItem() {
        if (itemList.isNotEmpty()) {
            itemList.removeAt(itemList.size-1)
            _currentItemListSize.value = itemList.size + 1
        }
    }
}