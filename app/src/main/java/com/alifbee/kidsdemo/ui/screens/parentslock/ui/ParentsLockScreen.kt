package com.alifbee.kidsdemo.ui.screens.parentslock.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alifbee.kidsdemo.R
import com.alifbee.kidsdemo.ui.common.AppTopBar
import com.alifbee.kidsdemo.ui.common.CollectEffect
import com.alifbee.kidsdemo.ui.screens.parentslock.ui.components.LockKeypad
import com.alifbee.kidsdemo.ui.screens.parentslock.ui.viewmodel.ParentsLockContract
import com.alifbee.kidsdemo.ui.theme.KidsDemoTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Composable
internal fun ParentsLockScreen(
    state: ParentsLockContract.State,
    effectFlow: Flow<ParentsLockContract.Effect>,
    onEventSent: (event: ParentsLockContract.Event) -> Unit,
    onBackClicked: () -> Unit,
    onNavigateToMoreApps: () -> Unit,
) {
    effectFlow.CollectEffect { effect ->
        when (effect) {
            ParentsLockContract.Effect.NavigateToMoreApps -> onNavigateToMoreApps()
        }
    }

    ParentsLockContent(
        state = state,
        onEventSent = onEventSent,
        onBackClicked = onBackClicked,
    )
}

@Composable
private fun ParentsLockContent(
    state: ParentsLockContract.State,
    onEventSent: (event: ParentsLockContract.Event) -> Unit,
    onBackClicked: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
            .systemBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AppTopBar(
            title = stringResource(R.string.parents_lock),
            onBackClicked = onBackClicked,
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.Center,
        ) {
            Image(
                painter = painterResource(R.drawable.img_parents_lock),
                contentDescription = stringResource(R.string.cd_illustration),
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .sizeIn(maxWidth = 250.dp, maxHeight = 250.dp)
                    .aspectRatio(1f),
            )
        }

        Text(
            text = stringResource(R.string.enter_the_numbers),
            color = MaterialTheme.colorScheme.onPrimary,
            fontSize = 14.sp,
        )
        Text(
            text = state.codeWords,
            color = MaterialTheme.colorScheme.onPrimary,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 4.dp, bottom = 16.dp),
        )

        PinRow(entered = state.entered)

        LockKeypad(
            onDigitClicked = { onEventSent(ParentsLockContract.Event.OnDigitClicked(it)) },
            onDeleteClicked = { onEventSent(ParentsLockContract.Event.OnDeleteClicked) },
            modifier = Modifier.padding(top = 24.dp, bottom = 24.dp),
        )
    }
}

@Composable
private fun PinRow(entered: List<Int>) {
    Row {
        PinCell(digit = entered.getOrNull(0))
        PinCell(digit = entered.getOrNull(1))
        PinCell(digit = entered.getOrNull(2))
    }
}

@Composable
private fun PinCell(digit: Int?) {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier.size(width = 44.dp, height = 40.dp),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = digit?.toString().orEmpty(),
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
            )
        }
        Box(
            modifier = Modifier
                .size(width = 44.dp, height = 3.dp)
                .background(MaterialTheme.colorScheme.onPrimary),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ParentsLockScreenPreview() {
    KidsDemoTheme {
        ParentsLockScreen(
            state = ParentsLockContract.State(
                codeWords = "Four, Nine, Six",
                entered = listOf(4, 9),
            ),
            effectFlow = emptyFlow(),
            onEventSent = {},
            onBackClicked = {},
            onNavigateToMoreApps = {},
        )
    }
}
