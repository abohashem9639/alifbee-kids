package com.alifbee.kidsdemo.ui.screens.moreapps.ui.navigation

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import com.alifbee.kidsdemo.ui.screens.moreapps.ui.MoreAppsScreen
import com.alifbee.kidsdemo.ui.screens.moreapps.ui.viewmodel.MoreAppsViewModel
import com.alifbee.kidsdemo.ui.utils.slideComposable
import kotlinx.serialization.Serializable

@Serializable
data object MoreAppsRoute

fun NavGraphBuilder.moreAppsRoute(
    isRtl: Boolean,
    onBackClicked: () -> Unit,
) {
    slideComposable<MoreAppsRoute>(isRtlDirection = isRtl) {
        val viewModel: MoreAppsViewModel = hiltViewModel()
        val state by viewModel.uiState.collectAsState()
        val context = LocalContext.current
        MoreAppsScreen(
            state = state,
            effectFlow = viewModel.effect,
            onEventSent = viewModel::setEvent,
            onBackClicked = onBackClicked,
            onOpenStorePage = { storeUrl ->
                try {
                    context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(storeUrl)))
                } catch (e: ActivityNotFoundException) {
                    e.printStackTrace()
                }
            },
        )
    }
}
