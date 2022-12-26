package com.june.simplecounter.dataLayer

import com.june.simplecounter.network.util.ApiResponseUtil
import com.june.simplecounter.network.UserInfoApi
import kotlinx.coroutines.*

/**
 * @param retrofit
 * @param ioDispatcher
 * @param mainDispatcher
 *
 * 종속 항목 삽입(Dependency injection, DI) 권장 사항을 따르려면 클래스 자체 CoroutineScope 를 만드는 대신 생성자의 매개변수로 범위를 수신해야한다.
 */

class UserInfoRemoteDataSource(
    private val retrofit: UserInfoApi,
    private val ioDispatcher: CoroutineDispatcher,
    private val mainDispatcher: CoroutineDispatcher
) {
    /**
     * 깃허브 서버에 유저 정보를 가져온다.
     * 서버와 통신하는 작업은 IO-optimized thread pool 에서 실행되며,
     * 응답값은 ViewModel 내부 LiveData 에 초기화되어야 하기 때문에 LiveData 초기화 작업은 UI-optimized thread pool(Main) 에서 실행된다.
     */
    fun fetchUser(callback:(String?) -> Unit) {
        try {
            CoroutineScope(ioDispatcher).launch {
                val response = retrofit
                    .fetchUserInfo()
                    .execute()

                withContext(mainDispatcher) {
                    callback(ApiResponseUtil().covertResponseToString(response))
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}