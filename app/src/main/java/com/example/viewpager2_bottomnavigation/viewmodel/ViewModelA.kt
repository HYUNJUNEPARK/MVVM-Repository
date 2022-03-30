package com.example.viewpager2_bottomnavigation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class ViewModelA: ViewModel() {
    private val _currentValue = MutableLiveData<Int>()
    private val _copyValue = MutableLiveData<Int>()

    val currentValue : LiveData<Int>
        get() = _currentValue
    val copyValue : LiveData<Int>
        get() = _copyValue

    init {
        _currentValue.value = 0
        _copyValue.value = 0
    }

    fun updateValue(value: Int) {
        when(value) {
            1 -> _currentValue.value = _currentValue.value?.plus(1)
            2 -> _currentValue.value = _currentValue.value?.minus(1)
        }
    }

    fun setValue() {
        _copyValue.value = _currentValue.value
    }
}