package com.june.simplecounter.ui_layer

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.june.simplecounter.data_layer.PreferenceManager
import com.june.simplecounter.data_layer.PreferenceManager.Companion.INIT_VALUE_ALIAS

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val context = getApplication<Application>().applicationContext
    private val preferenceManager = PreferenceManager.getInstance(context)

    val count: LiveData<Int?>
        get() = _count
    private var _count = MutableLiveData<Int?>()

    init {
        _count.value = preferenceManager.getInt(INIT_VALUE_ALIAS, 0)
    }

    fun increase() {
        _count.value = _count.value?.plus(1)
        preferenceManager.putInt(INIT_VALUE_ALIAS, _count.value)
    }

    fun decrease() {
        _count.value = _count.value?.minus(1)
        preferenceManager.putInt(INIT_VALUE_ALIAS, _count.value)
    }
}