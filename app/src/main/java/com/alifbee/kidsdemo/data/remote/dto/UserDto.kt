package com.alifbee.kidsdemo.data.remote.dto

import com.google.gson.annotations.SerializedName

data class UpdateProfileRequest(
    @SerializedName("learningLanguage") val learningLanguage: String,
)

data class UpdateProfileBody(
    @SerializedName("learningLanguage") val learningLanguage: String?,
)
