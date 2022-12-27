package com.june.simplecounter.dataLayer

import com.june.simplecounter.network.model.UserNameUiState
import com.june.simplecounter.util.ApiResponseUtil

class UserInfoRepository(
    private val remoteDataSource: UserInfoRemoteDataSource
) {
    suspend fun fetchUser(): String? {
        try {
            val responseBody = remoteDataSource.fetchUser()

            //네트워크에서 가져온 DataSource 에서 필요한 이름 정보만을 필터링
            val userNameUiState = UserNameUiState()
            if (responseBody != null) {
                for (user in (responseBody)!!.iterator()) {
                    userNameUiState.add(user.name)
                }
            }

            return ApiResponseUtil.jsonToString(userNameUiState)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
}