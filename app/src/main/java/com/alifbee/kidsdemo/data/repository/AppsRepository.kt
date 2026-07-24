package com.alifbee.kidsdemo.data.repository

import com.alifbee.kidsdemo.data.model.AppItem
import com.alifbee.kidsdemo.data.result.BaseRes

interface AppsRepository {
    suspend fun getApps(): BaseRes<List<AppItem>>
}
