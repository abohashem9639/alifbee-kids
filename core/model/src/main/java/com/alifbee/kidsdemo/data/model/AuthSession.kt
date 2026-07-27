package com.alifbee.kidsdemo.data.model

import kotlinx.serialization.Serializable

@Serializable
data class AuthSession(
    val userId: Int,
    val email: String,
    val fullName: String,
    val learningLanguage: String,
    val firstLogin: Boolean,
    val isGuest: Boolean,
    val token: String,
    val refreshToken: String,
)
