package com.alifbee.kidsdemo.ui.screens.moreapps.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alifbee.kidsdemo.R
import com.alifbee.kidsdemo.data.model.AppItem
import com.alifbee.kidsdemo.ui.common.AppTopBar
import com.alifbee.kidsdemo.ui.common.CollectEffect
import com.alifbee.kidsdemo.ui.screens.moreapps.ui.components.AppCard
import com.alifbee.kidsdemo.ui.screens.moreapps.ui.viewmodel.MoreAppsContract
import com.alifbee.kidsdemo.ui.screens.moreapps.ui.viewmodel.loading
import com.alifbee.kidsdemo.ui.theme.KidsDemoTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Composable
internal fun MoreAppsScreen(
    state: MoreAppsContract.State,
    effectFlow: Flow<MoreAppsContract.Effect>,
    onEventSent: (event: MoreAppsContract.Event) -> Unit,
    onBackClicked: () -> Unit,
    onOpenStorePage: (String) -> Unit,
) {
    effectFlow.CollectEffect { effect ->
        when (effect) {
            is MoreAppsContract.Effect.OpenStorePage -> onOpenStorePage(effect.storeUrl)
        }
    }

    MoreAppsContent(
        state = state,
        onEventSent = onEventSent,
        onBackClicked = onBackClicked,
    )
}

@Composable
private fun MoreAppsContent(
    state: MoreAppsContract.State,
    onEventSent: (event: MoreAppsContract.Event) -> Unit,
    onBackClicked: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
            .systemBarsPadding(),
    ) {
        AppTopBar(
            title = stringResource(R.string.more_by_alifbee),
            onBackClicked = onBackClicked,
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
        ) {
            when {
                state.fetchState.loading -> CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.align(Alignment.Center),
                )

                state.fetchState.error != null -> Text(
                    text = stringResource(R.string.apps_error),
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = { onEventSent(MoreAppsContract.Event.OnRetryClicked) },
                        )
                        .padding(16.dp),
                )

                else -> LazyColumn(
                    contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 40.dp, bottom = 28.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    items(state.apps, key = { it.id }) { app ->
                        AppCard(
                            app = app,
                            onClick = { onEventSent(MoreAppsContract.Event.OnAppClicked(app)) },
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MoreAppsScreenPreview() {
    KidsDemoTheme {
        MoreAppsScreen(
            state = MoreAppsContract.State(
                apps = listOf(
                    AppItem(
                        id = "1",
                        name = "AlifBee - Learn Arabic The Easy Way",
                        description = "Learn Arabic step by step with daily lessons.",
                        iconUrl = "",
                        storeUrl = "",
                    ),
                    AppItem(
                        id = "2",
                        name = "AlifBee Kids",
                        description = "Discover the fun world of Arabic with AlifBee Kids.",
                        iconUrl = "",
                        storeUrl = "",
                    ),
                ),
                fetchState = MoreAppsContract.FetchState.LoadData(),
            ),
            effectFlow = emptyFlow(),
            onEventSent = {},
            onBackClicked = {},
            onOpenStorePage = {},
        )
    }
}
