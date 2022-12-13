package com.example.networkretrofit.network.model.response


import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("documentation_url")
    val documentationUrl: String?,
    @SerializedName("message")
    val message: String?
)