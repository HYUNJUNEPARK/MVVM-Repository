package com.study.mvvmbasicex.network.model.exception


import com.google.gson.annotations.SerializedName

data class ResponseException(
    @SerializedName("code")
    val code: String?,
    @SerializedName("message")
    val message: String?
)