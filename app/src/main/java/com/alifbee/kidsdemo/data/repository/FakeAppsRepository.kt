package com.alifbee.kidsdemo.data.repository

import com.alifbee.kidsdemo.data.model.AppItem
import com.alifbee.kidsdemo.data.result.BaseRes
import kotlinx.coroutines.delay

class FakeAppsRepository : AppsRepository {

    override suspend fun getApps(): BaseRes<List<AppItem>> {
        delay(600)
        val description =
            "Discover the fun world of Arabic with the AlifBee Kids - Let them have fun while learning."
        return BaseRes.Body(
            listOf(
            AppItem(
                id = "1",
                name = "AlifBee - Learn Arabic The Easy Way",
                description = description,
                iconUrl = "",
                storeUrl = "https://play.google.com/store/apps/details?id=com.alifbee.app"
            ),
            AppItem(
                id = "2",
                name = "AlifBee - Arabic Word Treasure",
                description = description,
                iconUrl = "",
                storeUrl = "https://play.google.com/store/apps/details?id=com.alifbee.treasure"
            ),
            AppItem(
                id = "3",
                name = "AlifBee - Arabic Word Treasure",
                description = description,
                iconUrl = "",
                storeUrl = "https://play.google.com/store/apps/details?id=com.alifbee.quran"
            ),
            AppItem(
                id = "4",
                name = "AlifBee - Arabic Word Treasure",
                description = description,
                iconUrl = "",
                storeUrl = "https://play.google.com/store/apps/details?id=com.alifbee.hadith"
            )
            )
        )
    }
}
