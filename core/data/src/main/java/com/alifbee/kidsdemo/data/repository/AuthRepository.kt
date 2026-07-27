package com.alifbee.kidsdemo.data.repository

import com.alifbee.kidsdemo.data.model.AuthSession
import com.alifbee.kidsdemo.data.result.BaseRes

interface AuthRepository {
    suspend fun signIn(email: String, password: String): BaseRes<AuthSession>
}
