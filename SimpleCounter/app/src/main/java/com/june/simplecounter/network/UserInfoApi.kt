package com.june.simplecounter.network

import com.june.simplecounter.network.model.response.Repository
import retrofit2.Call
import retrofit2.http.GET

interface UserInfoApi {
    @GET("users/Kotlin/repos")
    fun fetchUserInfo(): Call<Repository>


}