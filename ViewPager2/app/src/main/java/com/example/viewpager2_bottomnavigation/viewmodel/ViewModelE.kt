package com.example.viewpager2_bottomnavigation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ViewModelE: ViewModel() {
    private val _currentItemListSize = MutableLiveData<Int>()
    val currentItemListSize: LiveData<Int>
        get() = _currentItemListSize

    private var _itemList: MutableList<String> = mutableListOf<String>()
    val itemList: MutableList<String>
        get() = _itemList

    init {
        _currentItemListSize.value = _itemList.size
    }

    fun addItem() {
        val _number = _itemList.size + 1
        val number = _number.toString()
        _itemList.add(number)
        _currentItemListSize.value = _itemList.size + 1
    }

    fun subItem() {
        if (_itemList.isNotEmpty()) {
            _itemList.removeAt(_itemList.size-1)
            _currentItemListSize.value = _itemList.size + 1
        }
    }
}