package com.june.simplecounter.data_layer

import com.june.simplecounter.network.ApiResponseUtil
import com.june.simplecounter.network.RetrofitObj
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class UserInfoRemoteDataSource(
    private val retrofitObj: RetrofitObj,
    private val ioDispatcher: CoroutineDispatcher
) {
    fun fetchUser(callback:(String) -> Unit) {
        try {
            CoroutineScope(ioDispatcher).launch {
                val response = retrofitObj.retrofit.fetchUserInfo().execute()
                callback(ApiResponseUtil().covertResponseToString(response))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}