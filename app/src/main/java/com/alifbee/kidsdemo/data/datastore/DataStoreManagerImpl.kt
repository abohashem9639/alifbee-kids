package com.alifbee.kidsdemo.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.alifbee.kidsdemo.data.model.AuthSession
import com.alifbee.kidsdemo.data.model.Syllabus
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json
import javax.inject.Inject

private val Context.dataStore by preferencesDataStore(name = "alifbee_prefs")

class DataStoreManagerImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : DataStoreManager {

    private val userSessionKey = stringPreferencesKey("user_session")
    private val syllabusKey = stringPreferencesKey("syllabus")

    override val userSession: Flow<AuthSession?> = context.dataStore.data.map { prefs ->
        prefs[userSessionKey]?.let { json ->
            runCatching { Json.decodeFromString<AuthSession>(json) }.getOrNull()
        }
    }

    override val userLoggedIn: Flow<Boolean> = userSession.map { session ->
        session != null && session.token.isNotBlank()
    }

    override val syllabus: Flow<Syllabus?> = context.dataStore.data.map { prefs ->
        prefs[syllabusKey]?.let { json ->
            runCatching { Json.decodeFromString<Syllabus>(json) }.getOrNull()
        }
    }

    override suspend fun setUserSession(session: AuthSession) {
        context.dataStore.edit { prefs ->
            prefs[userSessionKey] = Json.encodeToString(AuthSession.serializer(), session)
        }
    }

    override suspend fun clearUserSession() {
        context.dataStore.edit { prefs ->
            prefs.remove(userSessionKey)
            prefs.remove(syllabusKey)
        }
    }

    override suspend fun setSyllabus(syllabus: Syllabus) {
        context.dataStore.edit { prefs ->
            prefs[syllabusKey] = Json.encodeToString(Syllabus.serializer(), syllabus)
        }
    }
}
