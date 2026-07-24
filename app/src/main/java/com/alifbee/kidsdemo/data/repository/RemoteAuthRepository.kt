package com.alifbee.kidsdemo.data.repository

import android.content.Context
import android.provider.Settings
import com.alifbee.kidsdemo.data.model.AuthSession
import com.alifbee.kidsdemo.data.remote.ApiService
import com.alifbee.kidsdemo.data.remote.dto.SignInRequest
import com.alifbee.kidsdemo.data.remote.mapper.toDomain
import com.alifbee.kidsdemo.data.remote.safeApiCall
import com.alifbee.kidsdemo.data.result.BaseRes
import com.alifbee.kidsdemo.data.result.map
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class RemoteAuthRepository @Inject constructor(
    private val api: ApiService,
    @ApplicationContext private val context: Context,
) : AuthRepository {

    override suspend fun signIn(email: String, password: String): BaseRes<AuthSession> =
        safeApiCall {
            api.signIn(
                SignInRequest(
                    email = email,
                    password = password,
                    deviceId = deviceId(),
                    platform = "Android",
                )
            )
        }.map { it.toDomain() }

    private fun deviceId(): String =
        Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID).orEmpty()
}
