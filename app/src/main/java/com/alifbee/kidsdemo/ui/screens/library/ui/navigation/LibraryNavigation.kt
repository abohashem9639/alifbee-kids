package com.alifbee.kidsdemo.ui.screens.library.ui.navigation

import androidx.navigation.NavGraphBuilder
import com.alifbee.kidsdemo.ui.common.BottomBarNavRoute
import com.alifbee.kidsdemo.ui.screens.library.ui.LibraryScreen
import com.alifbee.kidsdemo.ui.utils.fadeComposable
import kotlinx.serialization.Serializable

@Serializable
data object LibraryRoute : BottomBarNavRoute

internal fun NavGraphBuilder.libraryRoute() {
    fadeComposable<LibraryRoute> {
        LibraryScreen()
    }
}
