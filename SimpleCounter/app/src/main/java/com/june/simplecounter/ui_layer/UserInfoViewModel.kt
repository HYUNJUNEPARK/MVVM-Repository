package com.june.simplecounter.ui_layer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.june.simplecounter.data_layer.UserInfoRemoteDataSource
import com.june.simplecounter.network.RetrofitObj
import com.june.simplecounter.network.model.UserNameUiState
import kotlinx.coroutines.Dispatchers

class UserInfoViewModel : ViewModel() {
    private val ioDispatcher = Dispatchers.IO
    private val mainDispatcher = Dispatchers.Main
    private val retrofitObj = RetrofitObj
    private val userInfoRemoteDataSource = UserInfoRemoteDataSource(
        retrofitObj = retrofitObj,
        ioDispatcher = ioDispatcher,
        mainDispatcher = mainDispatcher
    )

    val userInfo: LiveData<ArrayList<String>>
        get() = _userInfo
    private var _userInfo = MutableLiveData<ArrayList<String>>()

    fun fetchUserInfo() {
        val userNameUiState = UserNameUiState()

        userInfoRemoteDataSource.fetchUser { response ->
            if (response.isNullOrEmpty()) {
                return@fetchUser
            }

            //Repository : ArrayList<RepositoryItem> 에서 name 데이터만 추출
            for (user in response!!.iterator()) {
                userNameUiState.add(user.name)
            }

            _userInfo.value = userNameUiState
        }
    }
}