package com.alifbee.kidsdemo.ui.screens.splash.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alifbee.kidsdemo.core.designsystem.R
import com.alifbee.kidsdemo.ui.common.CollectEffect
import com.alifbee.kidsdemo.ui.screens.splash.ui.viewmodel.SplashContract
import com.alifbee.kidsdemo.ui.theme.KidsDemoTheme
import kotlinx.coroutines.flow.Flow

@Composable
internal fun SplashScreen(
    state: SplashContract.State,
    effectFlow: Flow<SplashContract.Effect>,
    onEventSent: (event: SplashContract.Event) -> Unit,
    onNavigateToHome: () -> Unit,
    onNavigateToLogin: () -> Unit,
) {
    effectFlow.CollectEffect { effect ->
        when (effect) {
            SplashContract.Effect.NavigateToHome -> onNavigateToHome()
            SplashContract.Effect.NavigateToLogin -> onNavigateToLogin()
        }
    }

    SplashContent()
}

@Composable
private fun SplashContent() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary),
        contentAlignment = Alignment.Center,
    ) {
        Image(
            painter = painterResource(R.drawable.img_alifbee),
            contentDescription = null,
            modifier = Modifier.width(191.dp),
        )
    }
}

@Preview
@Composable
private fun SplashScreenPreview() {
    KidsDemoTheme {
        SplashContent()
    }
}
