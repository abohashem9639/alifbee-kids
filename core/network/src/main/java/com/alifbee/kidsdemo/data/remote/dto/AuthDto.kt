package com.alifbee.kidsdemo.data.remote.dto

import com.google.gson.annotations.SerializedName

data class SignInRequest(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
    @SerializedName("deviceId") val deviceId: String,
    @SerializedName("platform") val platform: String,
)

data class SignInBody(
    @SerializedName("email") val email: String?,
    @SerializedName("fullName") val fullName: String?,
    @SerializedName("learningLanguage") val learningLanguage: String?,
    @SerializedName("firstLogin") val firstLogin: Boolean?,
    @SerializedName("refreshToken") val refreshToken: String?,
    @SerializedName("token") val token: String?,
    @SerializedName("userId") val userId: Int?,
    @SerializedName("isGuest") val isGuest: Boolean?,
)
