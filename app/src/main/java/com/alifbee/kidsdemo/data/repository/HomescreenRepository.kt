package com.alifbee.kidsdemo.data.repository

import com.alifbee.kidsdemo.data.model.HomescreenData

interface HomescreenRepository {
    suspend fun getHomescreen(): HomescreenData
}
