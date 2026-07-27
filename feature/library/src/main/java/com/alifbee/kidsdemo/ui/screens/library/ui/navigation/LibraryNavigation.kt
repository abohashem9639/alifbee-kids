package com.alifbee.kidsdemo.ui.screens.library.ui.navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.alifbee.kidsdemo.ui.common.BottomBarNavRoute
import com.alifbee.kidsdemo.ui.screens.library.ui.LibraryScreen
import com.alifbee.kidsdemo.ui.screens.library.ui.viewmodel.SyllabusViewModel
import com.alifbee.kidsdemo.ui.utils.fadeComposable
import com.alifbee.kidsdemo.ui.utils.sharedViewModel
import kotlinx.serialization.Serializable

@Serializable
data object LibraryGraphRoute

@Serializable
data object LibraryRoute : BottomBarNavRoute

fun NavGraphBuilder.libraryRoute(
    navController: NavController,
    onNavigateToLevels: () -> Unit,
    onNavigateToTargets: (Int) -> Unit,
) {
    fadeComposable<LibraryRoute> { backStackEntry ->
        val viewModel: SyllabusViewModel = backStackEntry.sharedViewModel(navController)
        val state by viewModel.uiState.collectAsState()
        LibraryScreen(
            state = state,
            onEventSent = viewModel::setEvent,
            onLevelSelectorClicked = onNavigateToLevels,
            onLessonClicked = { lesson -> onNavigateToTargets(lesson.id) },
        )
    }
}
