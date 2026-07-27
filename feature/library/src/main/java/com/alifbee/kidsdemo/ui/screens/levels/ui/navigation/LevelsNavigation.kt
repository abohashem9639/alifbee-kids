package com.alifbee.kidsdemo.ui.screens.levels.ui.navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.alifbee.kidsdemo.ui.screens.levels.ui.LevelsScreen
import com.alifbee.kidsdemo.ui.screens.library.ui.viewmodel.SyllabusViewModel
import com.alifbee.kidsdemo.ui.utils.sharedViewModel
import com.alifbee.kidsdemo.ui.utils.slideComposable
import kotlinx.serialization.Serializable

@Serializable
data object LevelsRoute

fun NavGraphBuilder.levelsRoute(
    navController: NavController,
    isRtl: Boolean,
    onCloseClicked: () -> Unit,
    onLevelSelected: () -> Unit,
) {
    slideComposable<LevelsRoute>(isRtlDirection = isRtl) { backStackEntry ->
        val viewModel: SyllabusViewModel = backStackEntry.sharedViewModel(navController)
        val state by viewModel.uiState.collectAsState()
        LevelsScreen(
            state = state,
            onEventSent = viewModel::setEvent,
            onCloseClicked = onCloseClicked,
            onLevelSelected = onLevelSelected,
        )
    }
}
