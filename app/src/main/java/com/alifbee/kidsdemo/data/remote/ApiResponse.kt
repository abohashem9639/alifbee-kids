package com.alifbee.kidsdemo.data.remote

import com.alifbee.kidsdemo.data.result.AppError
import com.alifbee.kidsdemo.data.result.BaseRes
import com.google.gson.annotations.SerializedName

data class ApiResponse<T>(
    @SerializedName("success") val success: Boolean,
    @SerializedName("error") val error: String?,
    @SerializedName("body") val body: T?,
)

fun <T> ApiResponse<T>.toBaseRes(): BaseRes<T> =
    if (success && body != null) {
        BaseRes.Body(body)
    } else {
        BaseRes.Error(AppError.General(error))
    }

suspend fun <T> safeApiCall(call: suspend () -> ApiResponse<T>): BaseRes<T> =
    try {
        call().toBaseRes()
    } catch (e: Exception) {
        BaseRes.Error(AppError.General(e.message))
    }
