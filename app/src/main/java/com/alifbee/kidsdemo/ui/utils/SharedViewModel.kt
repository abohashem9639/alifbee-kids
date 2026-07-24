package com.alifbee.kidsdemo.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController

@Composable
inline fun <reified VM : ViewModel> NavBackStackEntry.sharedViewModel(
    navController: NavController,
): VM {
    val parentRoute = destination.parent?.route ?: return hiltViewModel()
    val parentEntry = remember(this) { navController.getBackStackEntry(parentRoute) }
    return hiltViewModel(parentEntry)
}
