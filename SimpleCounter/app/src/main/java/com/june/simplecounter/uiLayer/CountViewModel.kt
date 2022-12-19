package com.june.simplecounter.uiLayer

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.june.simplecounter.dataLayer.CountLocalDataSource
import com.june.simplecounter.dataLayer.CountLocalDataSource.Companion.INIT_VALUE_ALIAS

class CountViewModel(application: Application) : AndroidViewModel(application) {
    private val context = getApplication<Application>().applicationContext
    private val countLocalDataSource = CountLocalDataSource.getInstance(context)

    val count: LiveData<Int?>
        get() = _count
    private var _count = MutableLiveData<Int?>()

    init {
        _count.value = countLocalDataSource.getInt(INIT_VALUE_ALIAS, 0)
    }

    fun increase() {
        _count.value = _count.value?.plus(1)
        countLocalDataSource.putInt(INIT_VALUE_ALIAS, _count.value)
    }

    fun decrease() {
        _count.value = _count.value?.minus(1)
        countLocalDataSource.putInt(INIT_VALUE_ALIAS, _count.value)
    }
}