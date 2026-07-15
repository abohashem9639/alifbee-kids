package com.alifbee.kidsdemo.data.repository

import com.alifbee.kidsdemo.data.model.AppItem

interface AppsRepository {
    suspend fun getApps(): List<AppItem>
}
