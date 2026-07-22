package com.alifbee.kidsdemo.ui.screens.dictionary.ui.navigation

import androidx.navigation.NavGraphBuilder
import com.alifbee.kidsdemo.ui.common.BottomBarNavRoute
import com.alifbee.kidsdemo.ui.screens.dictionary.ui.DictionaryScreen
import com.alifbee.kidsdemo.ui.utils.fadeComposable
import kotlinx.serialization.Serializable

@Serializable
data object DictionaryRoute : BottomBarNavRoute

internal fun NavGraphBuilder.dictionaryRoute() {
    fadeComposable<DictionaryRoute> {
        DictionaryScreen()
    }
}
