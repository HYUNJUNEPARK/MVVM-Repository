package com.june.simplecounter.data_layer

import com.june.simplecounter.network.ApiResponseUtil
import com.june.simplecounter.network.RetrofitObj
import com.june.simplecounter.network.model.response.Repository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class UserInfoRemoteDataSource(
    private val retrofitObj: RetrofitObj,
    private val ioDispatcher: CoroutineDispatcher
) {
    fun fetchUser(callback:(Repository?) -> Unit) {
        try {
            CoroutineScope(ioDispatcher).launch {
                val response = retrofitObj.retrofit.fetchUserInfo().execute()
                callback(ApiResponseUtil().covertResponseToDataClass(response))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}