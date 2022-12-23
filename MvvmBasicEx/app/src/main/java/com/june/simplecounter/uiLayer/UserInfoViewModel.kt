package com.june.simplecounter.uiLayer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.june.simplecounter.dataLayer.UserInfoRemoteDataSource
import com.june.simplecounter.network.RetrofitObj
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class UserInfoViewModel : ViewModel() {
    private val ioDispatcher = Dispatchers.IO
    private val mainDispatcher = Dispatchers.Main
    private val retrofitObj = RetrofitObj
    private val userInfoRemoteDataSource = UserInfoRemoteDataSource(
        retrofitObj = retrofitObj,
        ioDispatcher = ioDispatcher,
        mainDispatcher = mainDispatcher
    )

    private var _isUserInfoFetching = MutableLiveData<Boolean>()
    val isUserInfoFetching: LiveData<Boolean>
        get() = _isUserInfoFetching

    private var _userInfo = MutableStateFlow("")
    val userInfo: StateFlow<String>
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