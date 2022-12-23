package com.june.simplecounter.uiLayer

import androidx.lifecycle.SavedStateHandle
import kotlinx.coroutines.flow.StateFlow

class SavableMutableStateFlow<T>(
    private val savedStateHandle: SavedStateHandle,
    private val key: String,
    initialValue: T
) {
    companion object {
        const val KEY_COUNT = "count"
    }

    private val stateFlow: StateFlow<T> = savedStateHandle.getStateFlow(key, initialValue)
    var value: T
        get() = stateFlow.value
        set(value) {
            savedStateHandle[key] = value
        }
    fun asStateFlow(): StateFlow<T> = stateFlow
}

fun <T> SavedStateHandle.getMutableStateFlow(key: String, initialValue: T): SavableMutableStateFlow<T> =
    SavableMutableStateFlow(this, key, initialValue)
