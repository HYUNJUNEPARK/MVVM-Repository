package com.june.simplecounter.network

import retrofit2.Call
import retrofit2.http.GET

interface UserInfoApi {
    @GET("users/Kotlin/repos")
    fun fetchUserInfo(): Call<Any>
}