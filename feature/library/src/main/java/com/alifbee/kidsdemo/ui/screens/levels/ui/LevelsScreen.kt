package com.alifbee.kidsdemo.ui.screens.levels.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alifbee.kidsdemo.core.designsystem.R
import com.alifbee.kidsdemo.data.model.SyllabusCourse
import com.alifbee.kidsdemo.data.model.SyllabusLevel
import com.alifbee.kidsdemo.ui.screens.levels.ui.components.CourseHeaderCard
import com.alifbee.kidsdemo.ui.screens.levels.ui.components.LevelTimelineRow
import com.alifbee.kidsdemo.ui.screens.library.ui.viewmodel.SyllabusContract
import com.alifbee.kidsdemo.ui.screens.library.ui.viewmodel.loading
import com.alifbee.kidsdemo.ui.theme.KidsDemoTheme

@Composable
internal fun LevelsScreen(
    state: SyllabusContract.State,
    onEventSent: (event: SyllabusContract.Event) -> Unit,
    onCloseClicked: () -> Unit,
    onLevelSelected: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .statusBarsPadding(),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp),
        ) {
            Text(
                text = stringResource(R.string.levels_title),
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f),
            )
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .clickable(onClick = onCloseClicked),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_close),
                    contentDescription = stringResource(R.string.cd_close),
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.size(24.dp),
                )
            }
        }

        Box(modifier = Modifier.weight(1f)) {
            when {
                state.fetchState.loading -> CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.align(Alignment.Center),
                )

                state.fetchState.error != null -> Text(
                    text = stringResource(R.string.syllabus_error),
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = { onEventSent(SyllabusContract.Event.OnRetryClicked) },
                        )
                        .padding(16.dp),
                )

                else -> LazyColumn(
                    contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 24.dp),
                ) {
                    state.course?.let { course ->
                        item {
                            CourseHeaderCard(
                                course = course,
                                levelsCount = state.levelItems.size,
                                modifier = Modifier.padding(bottom = 16.dp),
                            )
                        }
                    }
                    itemsIndexed(state.levelItems, key = { _, item -> item.level.id }) { index, item ->
                        LevelTimelineRow(
                            item = item,
                            isLast = index == state.levelItems.lastIndex,
                            onClick = {
                                onEventSent(SyllabusContract.Event.OnLevelSelected(item.level.id))
                                onLevelSelected()
                            },
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LevelsScreenPreview() {
    KidsDemoTheme {
        LevelsScreen(
            state = SyllabusContract.State(
                course = SyllabusCourse(
                    id = 55,
                    slug = "LANG",
                    language = "RU",
                    icon = "",
                    title = "Arabic Mastery",
                    description = "",
                    order = 16,
                    levelCount = 4,
                    isPremium = false,
                ),
                levelItems = listOf(
                    SyllabusContract.LevelItem(
                        level = SyllabusLevel(id = 345, slug = "A0", title = "A0 - Beginner", description = "", order = 95),
                        lessonsCount = 37,
                        progress = 1f,
                    ),
                    SyllabusContract.LevelItem(
                        level = SyllabusLevel(id = 347, slug = "A2", title = "A2 - Novice mid", description = "", order = 97),
                        lessonsCount = 37,
                        progress = 0.4f,
                    ),
                    SyllabusContract.LevelItem(
                        level = SyllabusLevel(id = 348, slug = "A3", title = "A3 - Novice high", description = "", order = 98),
                        lessonsCount = 37,
                        progress = 0f,
                    ),
                ),
                fetchState = SyllabusContract.FetchState.LoadData(),
            ),
            onEventSent = {},
            onCloseClicked = {},
            onLevelSelected = {},
        )
    }
}
