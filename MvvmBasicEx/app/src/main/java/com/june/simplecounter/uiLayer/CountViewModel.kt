package com.june.simplecounter.uiLayer

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.june.simplecounter.dataLayer.CountLocalDataSource
import com.june.simplecounter.dataLayer.CountLocalDataSource.Companion.INIT_VALUE_ALIAS
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CountViewModel(application: Application) : AndroidViewModel(application) {
    private val context = getApplication<Application>().applicationContext
    private val countLocalDataSource = CountLocalDataSource.getInstance(context)

    private var _count =  MutableStateFlow(0)
    val count: StateFlow<Int>
        get() = _count

    init {
        _count.value = countLocalDataSource.getInt(INIT_VALUE_ALIAS, 0)
    }

    fun increase() {
        _count.value += 1
        countLocalDataSource.putInt(INIT_VALUE_ALIAS, _count.value)
    }

    fun decrease() {
        _count.value -= 1
        countLocalDataSource.putInt(INIT_VALUE_ALIAS, _count.value)
    }
}