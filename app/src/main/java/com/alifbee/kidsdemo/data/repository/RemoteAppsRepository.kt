package com.alifbee.kidsdemo.data.repository

import com.alifbee.kidsdemo.data.model.AppItem
import com.alifbee.kidsdemo.data.remote.ApiService
import com.alifbee.kidsdemo.data.remote.NetworkModule
import java.util.Locale

class RemoteAppsRepository(
    private val api: ApiService = NetworkModule.apiService
) : AppsRepository {

    override suspend fun getApps(): List<AppItem> {
        val body = api.getInitial().body
        val apps = body.ourApps[currentLanguage()]
            ?: body.ourApps[DEFAULT_LANGUAGE]
            ?: emptyList()

        return apps.map { dto ->
            AppItem(
                id = dto.androidLink.orEmpty(),
                name = dto.title.orEmpty(),
                description = dto.description.orEmpty(),
                iconUrl = dto.icon.orEmpty(),
                storeUrl = dto.androidLink.orEmpty()
            )
        }
    }

    private fun currentLanguage(): String = Locale.getDefault().language

    private companion object {
        const val DEFAULT_LANGUAGE = "en"
    }
}
