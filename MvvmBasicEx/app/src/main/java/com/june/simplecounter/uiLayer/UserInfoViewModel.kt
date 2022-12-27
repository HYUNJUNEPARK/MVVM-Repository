package com.june.simplecounter.uiLayer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.june.simplecounter.dataLayer.UserInfoRemoteDataSource
import com.june.simplecounter.dataLayer.UserInfoRepository
import com.june.simplecounter.network.UserInfoClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserInfoViewModel : ViewModel() {
    private val userInfoRemoteDataSource = UserInfoRemoteDataSource(
        userInfoApi = UserInfoClient.retrofit,
        ioDispatcher = Dispatchers.IO
    )

    private val repository = UserInfoRepository(userInfoRemoteDataSource)

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
        CoroutineScope(Dispatchers.IO).launch {
            val networkResult = repository.fetchUser()

            withContext(Dispatchers.Main) {
                if (networkResult.isNullOrEmpty()) {
                    _isUserInfoFetching.value = false
                    return@withContext
                }
                _userInfo.value = networkResult!!
                _isUserInfoFetching.value = false
            }
        }
        _isUserInfoFetching.value = true
    }
}