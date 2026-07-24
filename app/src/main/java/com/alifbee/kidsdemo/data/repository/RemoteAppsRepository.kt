package com.alifbee.kidsdemo.data.repository

import com.alifbee.kidsdemo.data.model.AppItem
import com.alifbee.kidsdemo.data.remote.ApiService
import com.alifbee.kidsdemo.data.remote.safeApiCall
import com.alifbee.kidsdemo.data.result.BaseRes
import com.alifbee.kidsdemo.data.result.map
import java.util.Locale
import javax.inject.Inject

class RemoteAppsRepository @Inject constructor(
    private val api: ApiService
) : AppsRepository {

    override suspend fun getApps(): BaseRes<List<AppItem>> =
        safeApiCall { api.getInitial() }.map { body ->
            val apps = body.ourApps[Locale.getDefault().language]
                ?: body.ourApps["en"]
                ?: emptyList()

            apps.map { dto ->
                AppItem(
                    id = dto.androidLink.orEmpty(),
                    name = dto.title.orEmpty(),
                    description = dto.description.orEmpty(),
                    iconUrl = dto.icon.orEmpty(),
                    storeUrl = dto.androidLink.orEmpty()
                )
            }
        }
}
