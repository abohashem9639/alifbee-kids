package com.alifbee.kidsdemo.ui.screens.home.ui

import android.R.attr.padding
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alifbee.kidsdemo.core.designsystem.R
import com.alifbee.kidsdemo.data.model.Category
import com.alifbee.kidsdemo.ui.common.CollectEffect
import com.alifbee.kidsdemo.ui.common.PushButton
import com.alifbee.kidsdemo.ui.screens.home.ui.components.CategoryCarousel
import com.alifbee.kidsdemo.ui.screens.home.ui.viewmodel.HomeContract
import com.alifbee.kidsdemo.ui.theme.KidsDemoTheme
import com.alifbee.kidsdemo.ui.theme.LocalCustomColors
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Composable
internal fun HomeScreen(
    state: HomeContract.State,
    effectFlow: Flow<HomeContract.Effect>,
    onEventSent: (event: HomeContract.Event) -> Unit,
    onNavigateToHomescreen: () -> Unit,
    onNavigateToParentsLock: () -> Unit,
) {
    effectFlow.CollectEffect { effect ->
        when (effect) {
            HomeContract.Effect.NavigateToHomescreen -> onNavigateToHomescreen()
            HomeContract.Effect.NavigateToParentsLock -> onNavigateToParentsLock()
        }
    }

    HomeContent(
        state = state,
        onEventSent = onEventSent,
    )
}

@Composable
private fun HomeContent(
    state: HomeContract.State,
    onEventSent: (event: HomeContract.Event) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {
        Image(
            painter = painterResource(R.drawable.cloud_1),
            contentDescription = null,
            modifier = Modifier.align(Alignment.TopStart),
        )
        Image(
            painter = painterResource(R.drawable.cloud_2),
            contentDescription = null,
            modifier = Modifier.align(Alignment.BottomStart),
        )
        Image(
            painter = painterResource(R.drawable.cloud_3),
            contentDescription = null,
            modifier = Modifier.align(Alignment.BottomEnd),
        )

        Column(modifier = Modifier.fillMaxSize()) {
            HomeHeader(
                isMusicOn = state.isMusicOn,
                onMusicClicked = { onEventSent(HomeContract.Event.OnMusicClicked) },
                onMoreByUsClicked = { onEventSent(HomeContract.Event.OnMoreByUsClicked) },
            )
            CategoryCarousel(
                categories = state.categories,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(bottom = 35.dp),
            )
        }
    }
}

@Composable
private fun HomeHeader(
    isMusicOn: Boolean,
    onMusicClicked: () -> Unit,
    onMoreByUsClicked: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(top = 16.dp)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = stringResource(R.string.exploration_path),
            color = MaterialTheme.colorScheme.onPrimary,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.weight(1f))
        Image(
            painter = painterResource(if (isMusicOn) R.drawable.ic_music_on else R.drawable.ic_music_off),
            contentDescription = stringResource(R.string.cd_background_music),
            modifier = Modifier
                .size(width = 36.dp, height = 37.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = onMusicClicked,
                ),
        )
        Spacer(modifier = Modifier.width(11.dp))
        PushButton(
            text = stringResource(R.string.more_by_us),
            onClick = onMoreByUsClicked,
            icon = painterResource(R.drawable.ic_alifbee),
            faceColor = MaterialTheme.colorScheme.primary,
            baseColor = LocalCustomColors.current.moreByUsBase,
        )
    }
}

@Preview(widthDp = 800, heightDp = 360, device = "id:resizable")
@Composable
private fun HomeScreenPreview() {
    KidsDemoTheme {
        HomeScreen(
            state = HomeContract.State(
                categories = listOf(
                    Category(R.drawable.img_games),
                    Category(R.drawable.img_videos),
                    Category(R.drawable.img_songs),
                ),
            ),
            effectFlow = emptyFlow(),
            onEventSent = {},
            onNavigateToHomescreen = {},
            onNavigateToParentsLock = {},
        )
    }
}
