package com.june.simplecounter.uiLayer

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import com.june.simplecounter.dataLayer.CountLocalDataSource
import com.june.simplecounter.dataLayer.CountLocalDataSource.Companion.INIT_VALUE_ALIAS
import com.june.simplecounter.uiLayer.SavableMutableStateFlow.Companion.KEY_COUNT
import kotlinx.coroutines.flow.StateFlow

class CountViewModel(
    application: Application,
    stateHandle: SavedStateHandle
) : AndroidViewModel(application) {
    private val countLocalDataSource = CountLocalDataSource.getInstance(
        _context = getApplication<Application>().applicationContext
    )

    private val _count = stateHandle.getMutableStateFlow(KEY_COUNT, 0)
    val count: StateFlow<Int>
        get() = _count.asStateFlow()

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