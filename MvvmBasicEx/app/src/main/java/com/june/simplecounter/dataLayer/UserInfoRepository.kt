package com.june.simplecounter.dataLayer

import com.june.simplecounter.network.model.UserNameUiState
import com.june.simplecounter.util.ApiResponseUtil
import kotlinx.coroutines.sync.Mutex

class UserInfoRepository(
    private val remoteDataSource: UserInfoRemoteDataSource
) {
    //Cache of userNameInfo got from the network
    private val userNameUiState = UserNameUiState()

    private val userNameUiStateMutex = Mutex()

    suspend fun fetchUser(): String? {
        try {

            //네트워크에서 가져온 DataSource 에서 필요한 이름 정보만을 필터링
            val networkResult = remoteDataSource.fetchUser()
            if (networkResult != null) {
                for (user in (networkResult)!!.iterator()) {
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