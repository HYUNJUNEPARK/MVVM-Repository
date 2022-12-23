package com.june.simplecounter.uiLayer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.june.simplecounter.dataLayer.UserInfoRemoteDataSource
import com.june.simplecounter.network.RetrofitObj
import com.june.simplecounter.network.model.UserNameUiState
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

    private var _userInfo = MutableStateFlow(arrayListOf("Empty"))
    val userInfo: StateFlow<ArrayList<String>>
        get() = _userInfo

    init {
        _isUserInfoFetching.value = false
    }

    fun fetchUserInfo() {
        _isUserInfoFetching.value = true

        val userNameUiState = UserNameUiState()

        userInfoRemoteDataSource.fetchUser { response ->
            if (response.isNullOrEmpty()) {
                _isUserInfoFetching.value = false
                return@fetchUser
            }
            for (user in response.iterator()) {
                userNameUiState.add(user.name)
            }

            _userInfo.value = userNameUiState
            _isUserInfoFetching.value = false
        }
    }
}