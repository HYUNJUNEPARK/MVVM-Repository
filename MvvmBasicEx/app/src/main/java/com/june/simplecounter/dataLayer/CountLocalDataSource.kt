package com.june.simplecounter.dataLayer

import android.content.Context
import android.content.SharedPreferences

/**
 * XML 파일 위치
 * data > data > 패키지명 > shared_prefs > pref.xml
 */

class CountLocalDataSource private constructor(context: Context) {
    companion object {
        const val COUNT_VALUE_ALIAS = "initValue"
        const val PREF_FILE_NAME = "PREF"
        private var instance: CountLocalDataSource? = null

        fun getInstance(_context: Context): CountLocalDataSource {
            return instance ?: synchronized(this) {
                instance ?: CountLocalDataSource(_context).also {
                    instance = it
                }
            }
        }
    }

    private val prefs: SharedPreferences
    private val prefsEditor: SharedPreferences.Editor

    init {
        prefs = context.getSharedPreferences(
            PREF_FILE_NAME,
            Context.MODE_PRIVATE
        )
        prefsEditor = prefs.edit()
    }

    fun getInt(key: String, defValue: Int): Int {
        return prefs.getInt(key, defValue)
    }

    fun putInt(key: String, value: Int) {
        prefsEditor.apply {
            putInt(key, value)
            apply()
        }
    }
}