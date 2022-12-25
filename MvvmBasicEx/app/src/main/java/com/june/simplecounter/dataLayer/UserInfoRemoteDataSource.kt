package com.june.simplecounter.dataLayer

import com.june.simplecounter.network.util.ApiResponseUtil
import com.june.simplecounter.network.RetrofitObj
import kotlinx.coroutines.*

class UserInfoRemoteDataSource(private val retrofitObj: RetrofitObj) {
    fun fetchUser(callback:(String?) -> Unit) {
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val response = retrofitObj.retrofit
                    .fetchUserInfo()
                    .execute()

                withContext(Dispatchers.Main) {
                    callback(ApiResponseUtil().covertResponseToString(response))
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}