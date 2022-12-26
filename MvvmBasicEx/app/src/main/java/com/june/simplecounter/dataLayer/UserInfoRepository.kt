package com.june.simplecounter.dataLayer

import android.util.Log
import com.june.simplecounter.network.model.UserNameUiState

class UserInfoRepository(
    private val dataSource: UserInfoRemoteDataSource
) {
    fun fetchUser() {
        dataSource.fetchUser { responseBody ->
            if (responseBody.isNullOrEmpty().not()) {
                    val userNameUiState = UserNameUiState()
                    for (user in (responseBody)!!.iterator()) {
                        userNameUiState.add(user.name)
                    }
                Log.d("testLog", "fetchUser: $userNameUiState")
            }
        }
    }
}