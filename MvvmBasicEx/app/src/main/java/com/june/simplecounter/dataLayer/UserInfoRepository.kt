package com.june.simplecounter.dataLayer

import android.util.Log
import com.june.simplecounter.network.model.UserNameUiState
import com.june.simplecounter.util.ApiResponseUtil
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class UserInfoRepository(
    private val remoteDataSource: UserInfoRemoteDataSource
) {
    //Cache of userNameInfo got from the network
    private val userNameUiState = UserNameUiState()

    //Mutex to make writes to cached value thread-safe
    private val userNameUiStateMutex = Mutex()

    suspend fun fetchUser(): String? {
        try {
            //Thread-safe write to userName
            //withLock 블럭 내부에 있는 비지니스 로직의 경우 동시성이 일어나지 않게 하고 하나의 루틴만 접근하는 것을 보장한다.
            userNameUiStateMutex.withLock {
                //네트워크에서 가져온 DataSource 에서 필요한 이름 정보만을 필터링
                val networkResult = remoteDataSource.fetchUser()
                if (networkResult != null) {
                    for (userInfo in (networkResult)!!.iterator()) {
                        userNameUiState.add(userInfo.name)
                    }
                }
                return ApiResponseUtil.jsonToString(userNameUiState)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
}