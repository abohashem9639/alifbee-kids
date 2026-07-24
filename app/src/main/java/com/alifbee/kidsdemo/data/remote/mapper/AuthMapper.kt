package com.alifbee.kidsdemo.data.remote.mapper

import com.alifbee.kidsdemo.data.model.AuthSession
import com.alifbee.kidsdemo.data.remote.dto.SignInBody

fun SignInBody.toDomain(): AuthSession = AuthSession(
    userId = userId ?: 0,
    email = email.orEmpty(),
    fullName = fullName.orEmpty(),
    learningLanguage = learningLanguage.orEmpty(),
    firstLogin = firstLogin == true,
    isGuest = isGuest == true,
    token = token.orEmpty(),
    refreshToken = refreshToken.orEmpty(),
)
