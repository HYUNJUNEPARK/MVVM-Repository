package com.june.simplecounter.data.remote

import com.june.simplecounter.util.ApiResponseUtil
import com.june.simplecounter.network.UserInfoApi
import com.june.simplecounter.network.model.response.Repository
import kotlinx.coroutines.*

/**
 * @param userInfoApi
 * @param ioDispatcher
 *
 * 종속 항목 삽입(Dependency injection, DI) 권장 사항을 따르려면 클래스 자체 CoroutineScope 를 만드는 대신 생성자의 매개변수로 범위를 수신해야한다.
 */

class UserInfoRemoteDataSource(
    private val userInfoApi: UserInfoApi,
    private val ioDispatcher: CoroutineDispatcher
) {
    /**
     * 깃허브 서버에 유저 정보를 가져온다.
     * 서버와 통신하는 작업은 IO-optimized thread pool 에서 실행한다.
     */
    suspend fun fetchUser(): Repository? {
        try {
            val result = withContext(ioDispatcher) {
                val response = userInfoApi
                    .fetchUserInfo()
                    .execute()
                ApiResponseUtil.covertResponseToDataClass(response)
            }
            return result
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
}