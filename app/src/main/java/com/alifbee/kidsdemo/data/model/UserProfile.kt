package com.alifbee.kidsdemo.data.model

data class UserProfile(
    val name: String,
    val email: String,
    val avatarUrl: String?,
    val isPremium: Boolean,
)
