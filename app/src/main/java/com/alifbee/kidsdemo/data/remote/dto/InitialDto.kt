package com.alifbee.kidsdemo.data.remote.dto

import com.google.gson.annotations.SerializedName

data class InitialBody(
    @SerializedName("our_apps") val ourApps: Map<String, List<AppDto>>
)

data class AppDto(
    @SerializedName("icon") val icon: String?,
    @SerializedName("title") val title: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("android_link") val androidLink: String?,
    @SerializedName("ios_link") val iosLink: String?
)
