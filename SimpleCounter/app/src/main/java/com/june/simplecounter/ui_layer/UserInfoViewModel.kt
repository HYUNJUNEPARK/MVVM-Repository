package com.june.simplecounter.ui_layer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.june.simplecounter.data_layer.UserInfoRemoteDataSource
import com.june.simplecounter.network.RetrofitObj
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserInfoViewModel(): ViewModel() {
    private val ioDispatcher = Dispatchers.IO
    private val retrofitObj = RetrofitObj

    private val userInfoRemoteDataSource = UserInfoRemoteDataSource(
        retrofitObj = retrofitObj,
        ioDispatcher = ioDispatcher
    )

    val userInfo: LiveData<String>
        get() = _userInfo
    private var _userInfo = MutableLiveData<String>()

    fun fetchUserInfo() {
        userInfoRemoteDataSource.fetchUser { response ->
            CoroutineScope(Dispatchers.Main).launch {
                _userInfo.value = response
            }
        }
    }
}