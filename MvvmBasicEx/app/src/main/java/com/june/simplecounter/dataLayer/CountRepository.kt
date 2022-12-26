package com.june.simplecounter.dataLayer

import com.june.simplecounter.dataLayer.CountLocalDataSource.Companion.COUNT_VALUE_ALIAS

class CountRepository(
    private val dataSource: CountLocalDataSource
) {
    fun getInt(): Int {
        return dataSource.getInt(COUNT_VALUE_ALIAS, 0)
    }

    fun putInt(total: Int) {
        dataSource.putInt(COUNT_VALUE_ALIAS, total)
    }
}