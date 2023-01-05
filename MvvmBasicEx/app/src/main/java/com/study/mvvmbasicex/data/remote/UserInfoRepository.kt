package com.study.mvvmbasicex.data.remote

import com.study.mvvmbasicex.network.model.UserNameUiState
import com.study.mvvmbasicex.util.ApiResponseUtil
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class UserInfoRepository(
    private val remoteDataSource: UserInfoRemoteDataSource
) {
    //Cache of userNameInfo got from the network
    private val userNameUiState = UserNameUiState()

    //Mutex to make writes to cached value thread-safe
    private val userNameUiStateMutex = Mutex()

    suspend fun fetchUser(refresh: Boolean = false): String? {
        try {
            if (refresh || userNameUiState.isEmpty()) {
                //Thread-safe write to userName
                //withLock 블럭 내부에 있는 비지니스 로직의 경우 동시성이 일어나지 않게 하고 하나의 루틴만 접근하는 것을 보장한다.
                userNameUiStateMutex.withLock {
                    userNameUiState.clear() //refresh 의 경우 캐시데이터가 남아있기 때문에 캐시를 지워주는 작업을 한다.
                    //네트워크에서 가져온 DataSource 에서 필요한 이름 정보만을 필터링
                    val networkResult = remoteDataSource.fetchUser()
                    if (networkResult != null) {
                        for (userInfo in (networkResult)!!.iterator()) {
                            userNameUiState.add(userInfo.name)
                        }
                    }
                    return ApiResponseUtil.jsonToString(userNameUiState)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
}