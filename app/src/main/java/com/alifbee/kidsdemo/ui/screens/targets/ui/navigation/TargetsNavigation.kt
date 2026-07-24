package com.alifbee.kidsdemo.ui.screens.targets.ui.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.toRoute
import com.alifbee.kidsdemo.ui.screens.library.ui.viewmodel.SyllabusContract
import com.alifbee.kidsdemo.ui.screens.library.ui.viewmodel.SyllabusViewModel
import com.alifbee.kidsdemo.ui.screens.targets.ui.TargetsScreen
import com.alifbee.kidsdemo.ui.utils.sharedViewModel
import com.alifbee.kidsdemo.ui.utils.slideComposable
import kotlinx.serialization.Serializable

@Serializable
data class TargetsRoute(val lessonId: Int)

internal fun NavGraphBuilder.targetsRoute(
    navController: NavController,
    isRtl: Boolean,
    onCloseClicked: () -> Unit,
) {
    slideComposable<TargetsRoute>(isRtlDirection = isRtl) { backStackEntry ->
        val lessonId = backStackEntry.toRoute<TargetsRoute>().lessonId
        val viewModel: SyllabusViewModel = backStackEntry.sharedViewModel(navController)
        val state by viewModel.uiState.collectAsState()
        LaunchedEffect(lessonId) {
            viewModel.setEvent(SyllabusContract.Event.OnLessonOpened(lessonId))
        }
        TargetsScreen(
            state = state,
            onEventSent = viewModel::setEvent,
            onCloseClicked = onCloseClicked,
        )
    }
}
