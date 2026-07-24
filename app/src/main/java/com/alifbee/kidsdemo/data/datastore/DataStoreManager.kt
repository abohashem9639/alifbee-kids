package com.alifbee.kidsdemo.data.datastore

import com.alifbee.kidsdemo.data.model.AuthSession
import kotlinx.coroutines.flow.Flow

interface DataStoreManager {
    val userSession: Flow<AuthSession?>
    val userLoggedIn: Flow<Boolean>
    val selectedLevelId: Flow<Int?>
    val appLanguage: Flow<String>
    suspend fun setUserSession(session: AuthSession)
    suspend fun clearUserSession()
    suspend fun setSelectedLevelId(levelId: Int)
    suspend fun setAppLanguage(code: String)
}
