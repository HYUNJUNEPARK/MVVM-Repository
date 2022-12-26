package com.june.simplecounter.uiLayer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.june.simplecounter.dataLayer.UserInfoRemoteDataSource
import com.june.simplecounter.network.UserInfoClient
import kotlinx.coroutines.Dispatchers

class UserInfoViewModel : ViewModel() {
    private val userInfoRemoteDataSource = UserInfoRemoteDataSource(
        userInfoApi = UserInfoClient.retrofit,
        mainDispatcher = Dispatchers.Main,
        ioDispatcher = Dispatchers.IO
    )

    private var _isUserInfoFetching = MutableLiveData<Boolean>()
    val isUserInfoFetching: LiveData<Boolean>
        get() = _isUserInfoFetching

    private var _userInfo = MutableLiveData<String>()
    val userInfo: LiveData<String>
        get() = _userInfo

    init {
        _isUserInfoFetching.value = false
    }

    fun fetchUserInfo() {
        _isUserInfoFetching.value = true

        userInfoRemoteDataSource.fetchUser { response ->
            if (response.isNullOrEmpty()) {
                _isUserInfoFetching.value = false
                return@fetchUser
            }
            _userInfo.value = response
            _isUserInfoFetching.value = false
        }
    }
}