package com.june.simplecounter.network.util

import com.june.simplecounter.network.model.exception.ResponseException
import com.google.gson.Gson
import com.june.simplecounter.network.model.UserNameUiState
import com.june.simplecounter.network.model.response.Repository
import retrofit2.Response

/**
 * 서버 응답을 반환한다.
 */
class ApiResponseUtil {
    private val gson = Gson()
    private val errorCode = "999"
    private val exceptionMessageBodyEmpty = "Exception : Response Body Empty"

    //response 의 body 데이터만 추출해 반환한다.
    fun covertResponseToDataClass(response: Response<Repository>): Repository? {
        try {
            if (response.body() != null) {
                return response.body()!!
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    //response 코드에 따라 서버 응답을 JSON String 으로 반환한다.
    fun covertResponseToString(response: Response<Repository>): String {
        when (response.code()) {
            200, 201 -> {
                if (response.body() != null) {
                    return gson.toJson(response.body())
                }
                return handleExceptionResponse(response)
            }
            400, 404, 500 -> {
                if (response.errorBody() != null) {
                    return response.errorBody()!!.string()
                }
                return handleExceptionResponse(response)
            }
            else -> {
                return handleExceptionResponse(response)
            }
        }
    }

    //response 코드와 메시지를 JSON String 으로 반환한다.
    private fun handleExceptionResponse(response: Response<Repository>): String {
        try {
            val message = if (response.errorBody() != null) {
                response.errorBody()!!.string()
            } else if (response.body() != null) {
                response.body().toString()
            } else {
                exceptionMessageBodyEmpty
            }
            //DataClass -> JSON String
            return gson.toJson(
                ResponseException(
                    code = response.code().toString(),
                    message = message
                )
            )
        } catch (e: Exception) {
            return handleExceptionResponse(e.toString())
        }
    }

    //Overloading method
    private fun handleExceptionResponse(message: String): String {
        //DataClass -> JSON String
        return gson.toJson(
            ResponseException(
                code = errorCode,
                message = message
            )
        )
    }
}