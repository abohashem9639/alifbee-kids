package com.alifbee.kidsdemo.data.repository

import com.alifbee.kidsdemo.data.datastore.DataStoreManager
import com.alifbee.kidsdemo.data.remote.ApiService
import com.alifbee.kidsdemo.data.remote.dto.UpdateProfileRequest
import com.alifbee.kidsdemo.data.result.AppError
import com.alifbee.kidsdemo.data.result.BaseRes
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class RemoteUserRepository @Inject constructor(
    private val api: ApiService,
    private val dataStoreManager: DataStoreManager,
) : UserRepository {

    override suspend fun updateLearningLanguage(code: String): BaseRes<Unit> =
        try {
            val response = api.updateUserProfile(UpdateProfileRequest(learningLanguage = code))
            if (response.success) {
                dataStoreManager.userSession.first()?.let { session ->
                    dataStoreManager.setUserSession(session.copy(learningLanguage = code))
                }
                BaseRes.Body(Unit)
            } else {
                BaseRes.Error(AppError.General(response.error))
            }
        } catch (e: Exception) {
            BaseRes.Error(AppError.General(e.message))
        }
}
