package com.june.simplecounter.data_layer

import android.content.Context
import android.content.SharedPreferences

/**
 * XML 파일 위치
 * data > data > 패키지명 > shared_prefs > pref.xml
 */

class CountLocalDataSource private constructor(context: Context) {
    companion object {
        const val INIT_VALUE_ALIAS = "initValue"
        private var instance: CountLocalDataSource? = null

        fun getInstance(_context: Context): CountLocalDataSource {
            return instance ?: synchronized(this) {
                instance ?: CountLocalDataSource(_context).also {
                    instance = it
                }
            }
        }
    }

    private val prefFileName = "pref"
    private val prefs: SharedPreferences
    private val prefsEditor: SharedPreferences.Editor

    init {
        prefs = context.getSharedPreferences(
            prefFileName,
            Context.MODE_PRIVATE
        )
        prefsEditor = prefs.edit()
    }

    fun getString(key: String?, defValue: String?): String {
        return prefs.getString(key, defValue)!!
    }

    fun putString(key: String?, value: String?) {
        prefsEditor.apply {
            putString(key, value)
            apply()
        }
    }

    fun getInt(key: String?, defValue: Int): Int {
        return prefs.getInt(key, defValue)
    }

    fun putInt(key: String?, value: Int?) {
        prefsEditor.apply {
            putInt(key, value!!)
            apply()
        }
    }

    fun getBoolean(key: String?, defValue: Boolean): Boolean {
        return prefs.getBoolean(key, defValue)
    }

    fun putBoolean(key: String?, value: Boolean) {
        prefsEditor.apply {
            putBoolean(key, value)
            apply()
        }
    }

    fun getKeyList(): List<String> {
        val keys:Map<String, *> = prefs.all
        val keyList:MutableList<String> = mutableListOf()
        for ((key, value) in keys.entries) {
            keyList.add(key)
        }
        return keyList
    }

    fun remove(key: String) {
        prefsEditor.apply {
            remove(key)
            apply()
        }
    }

    fun removeAll() {
        prefsEditor.apply {
            clear()
            apply()
        }
    }
}