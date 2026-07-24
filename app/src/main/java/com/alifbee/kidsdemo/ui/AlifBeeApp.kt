package com.alifbee.kidsdemo.ui

import android.content.pm.ActivityInfo
import androidx.activity.compose.LocalActivity
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.alifbee.kidsdemo.R
import com.alifbee.kidsdemo.ui.screens.dictionary.ui.navigation.DictionaryRoute
import com.alifbee.kidsdemo.ui.screens.dictionary.ui.navigation.dictionaryRoute
import com.alifbee.kidsdemo.ui.screens.home.ui.navigation.HomeRoute
import com.alifbee.kidsdemo.ui.screens.home.ui.navigation.homeRoute
import com.alifbee.kidsdemo.ui.screens.homescreen.ui.navigation.HomescreenRoute
import com.alifbee.kidsdemo.ui.screens.homescreen.ui.navigation.homescreenRoute
import com.alifbee.kidsdemo.ui.screens.language.ui.navigation.ChangeLanguageRoute
import com.alifbee.kidsdemo.ui.screens.language.ui.navigation.changeLanguageRoute
import com.alifbee.kidsdemo.ui.screens.levels.ui.navigation.LevelsRoute
import com.alifbee.kidsdemo.ui.screens.levels.ui.navigation.levelsRoute
import com.alifbee.kidsdemo.ui.screens.library.ui.navigation.LibraryGraphRoute
import com.alifbee.kidsdemo.ui.screens.library.ui.navigation.LibraryRoute
import com.alifbee.kidsdemo.ui.screens.library.ui.navigation.libraryRoute
import com.alifbee.kidsdemo.ui.screens.login.ui.navigation.LoginRoute
import com.alifbee.kidsdemo.ui.screens.login.ui.navigation.loginRoute
import com.alifbee.kidsdemo.ui.screens.moreapps.ui.navigation.MoreAppsRoute
import com.alifbee.kidsdemo.ui.screens.moreapps.ui.navigation.moreAppsRoute
import com.alifbee.kidsdemo.ui.screens.parentslock.ui.navigation.ParentsLockRoute
import com.alifbee.kidsdemo.ui.screens.parentslock.ui.navigation.parentsLockRoute
import com.alifbee.kidsdemo.ui.screens.review.ui.navigation.ReviewRoute
import com.alifbee.kidsdemo.ui.screens.review.ui.navigation.reviewRoute
import com.alifbee.kidsdemo.ui.screens.splash.ui.navigation.SplashRoute
import com.alifbee.kidsdemo.ui.screens.splash.ui.navigation.splashRoute
import com.alifbee.kidsdemo.ui.screens.targets.ui.navigation.TargetsRoute
import com.alifbee.kidsdemo.ui.screens.targets.ui.navigation.targetsRoute
import com.alifbee.kidsdemo.ui.theme.LocalCustomColors

@Composable
fun AlifBeeApp() {
    val navController = rememberNavController()
    val isRtl = LocalLayoutDirection.current == LayoutDirection.Rtl
    val activity = LocalActivity.current

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val showBottomBar = bottomBarTabs.any { tab ->
        currentDestination?.hasRoute(tab.route::class) == true
    }

    SystemScreenBehavior(currentDestination = currentDestination)

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        contentWindowInsets = WindowInsets(0.dp),
        bottomBar = {
            if (showBottomBar) {
                AppBottomBar(
                    currentDestination = currentDestination,
                    onTabSelected = { route ->
                        navController.navigate(route) {
                            popUpTo(HomescreenRoute) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                )
            }
        },
    ) { contentPadding ->
        NavHost(
            navController = navController,
            startDestination = SplashRoute,
            modifier = Modifier.padding(contentPadding),
        ) {
            splashRoute(
                onNavigateToHome = {
                    navController.navigate(HomescreenRoute) {
                        popUpTo(SplashRoute) { inclusive = true }
                    }
                },
                onNavigateToLogin = {
                    navController.navigate(LoginRoute) {
                        popUpTo(SplashRoute) { inclusive = true }
                    }
                },
            )
            homeRoute(
                onNavigateToHomescreen = { navController.navigate(HomescreenRoute) },
                onNavigateToParentsLock = { navController.navigate(ParentsLockRoute) },
            )
            parentsLockRoute(
                isRtl = isRtl,
                onBackClicked = { navController.popBackStack() },
                onNavigateToMoreApps = {
                    navController.navigate(MoreAppsRoute) {
                        popUpTo(ParentsLockRoute) { inclusive = true }
                    }
                },
            )
            moreAppsRoute(
                isRtl = isRtl,
                onBackClicked = { navController.popBackStack() },
            )
            homescreenRoute(
                onNavigateToChangeLanguage = { navController.navigate(ChangeLanguageRoute) },
            )
            changeLanguageRoute(
                isRtl = isRtl,
                onCloseClicked = { navController.popBackStack() },
                onDone = { navController.popBackStack() },
            )
            navigation<LibraryGraphRoute>(startDestination = LibraryRoute) {
                libraryRoute(
                    navController = navController,
                    onNavigateToLevels = { navController.navigate(LevelsRoute) },
                    onNavigateToTargets = { lessonId -> navController.navigate(TargetsRoute(lessonId)) },
                )
                levelsRoute(
                    navController = navController,
                    isRtl = isRtl,
                    onCloseClicked = { navController.popBackStack() },
                    onLevelSelected = { navController.popBackStack() },
                )
                targetsRoute(
                    navController = navController,
                    isRtl = isRtl,
                    onCloseClicked = { navController.popBackStack() },
                )
            }
            dictionaryRoute()
            reviewRoute()
            loginRoute(
                isRtl = isRtl,
                onCloseClicked = {
                    if (!navController.popBackStack()) activity?.finish()
                },
                onSignInSuccessNavigate = {
                    navController.navigate(HomescreenRoute) {
                        popUpTo(LoginRoute) { inclusive = true }
                        launchSingleTop = true
                    }
                },
            )
        }
    }
}

@Composable
private fun SystemScreenBehavior(currentDestination: NavDestination?) {
    val activity = LocalActivity.current ?: return
    LaunchedEffect(currentDestination) {
        val destination = currentDestination ?: return@LaunchedEffect
        val window = activity.window
        val insetsController = WindowInsetsControllerCompat(window, window.decorView)
        if (destination.hasRoute(HomeRoute::class)) {
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            insetsController.hide(WindowInsetsCompat.Type.systemBars())
            insetsController.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            insetsController.isAppearanceLightStatusBars = true
        } else {
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            insetsController.show(WindowInsetsCompat.Type.systemBars())
            insetsController.isAppearanceLightStatusBars =
                destination.hasRoute(HomescreenRoute::class) ||
                    destination.hasRoute(LibraryRoute::class) ||
                    destination.hasRoute(DictionaryRoute::class) ||
                    destination.hasRoute(LoginRoute::class) ||
                    destination.hasRoute(LevelsRoute::class) ||
                    destination.hasRoute(TargetsRoute::class) ||
                    destination.hasRoute(ChangeLanguageRoute::class)
        }
    }
}

private data class BottomBarTab(
    val route: Any,
    @DrawableRes val iconRes: Int,
    @StringRes val labelRes: Int,
)

private val bottomBarTabs = listOf(
    BottomBarTab(HomescreenRoute, R.drawable.ic_home, R.string.tab_home),
    BottomBarTab(LibraryRoute, R.drawable.ic_book, R.string.tab_library),
    BottomBarTab(DictionaryRoute, R.drawable.ic_open_book, R.string.tab_dictionary),
    BottomBarTab(ReviewRoute, R.drawable.ic_borsa, R.string.tab_review),
)

@Composable
private fun AppBottomBar(
    currentDestination: NavDestination?,
    onTabSelected: (Any) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(LocalCustomColors.current.plumPurple)
            .navigationBarsPadding()
            .height(56.dp),
    ) {
        bottomBarTabs.forEach { tab ->
            val selected = currentDestination?.hierarchy?.any {
                it.hasRoute(tab.route::class)
            } == true
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clickable(onClick = { onTabSelected(tab.route) }),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    painter = painterResource(tab.iconRes),
                    contentDescription = stringResource(tab.labelRes),
                    tint = if (selected) {
                        LocalCustomColors.current.brandYellow
                    } else {
                        MaterialTheme.colorScheme.onPrimary
                    },
                    modifier = Modifier.size(24.dp),
                )
            }
        }
    }
}
