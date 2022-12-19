package com.june.simplecounter.data_layer

import com.june.simplecounter.network.util.ApiResponseUtil
import com.june.simplecounter.network.RetrofitObj
import com.june.simplecounter.network.model.response.Repository
import kotlinx.coroutines.*

class UserInfoRemoteDataSource(
    private val retrofitObj: RetrofitObj,
    private val ioDispatcher: CoroutineDispatcher,
    private val mainDispatcher: CoroutineDispatcher
) {
    fun fetchUser(callback:(Repository?) -> Unit) {
        try {
            CoroutineScope(ioDispatcher).launch {
                val response = retrofitObj.retrofit
                    .fetchUserInfo()
                    .execute()

                withContext(mainDispatcher) {
                    callback(ApiResponseUtil().covertResponseToDataClass(response))
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}