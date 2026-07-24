package com.alifbee.kidsdemo.ui.screens.targets.ui

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
import com.alifbee.kidsdemo.R
import com.alifbee.kidsdemo.data.model.SyllabusLesson
import com.alifbee.kidsdemo.data.model.SyllabusTarget
import com.alifbee.kidsdemo.ui.screens.library.ui.viewmodel.SyllabusContract
import com.alifbee.kidsdemo.ui.screens.library.ui.viewmodel.loading
import com.alifbee.kidsdemo.ui.screens.targets.ui.components.LessonHeaderCard
import com.alifbee.kidsdemo.ui.screens.targets.ui.components.TargetTimelineRow
import com.alifbee.kidsdemo.ui.theme.KidsDemoTheme

@Composable
internal fun TargetsScreen(
    state: SyllabusContract.State,
    onEventSent: (event: SyllabusContract.Event) -> Unit,
    onCloseClicked: () -> Unit,
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
                text = stringResource(R.string.targets_title),
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
            val lesson = state.openedLesson
            when {
                lesson == null && state.fetchState.error != null -> Text(
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

                lesson == null -> CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.align(Alignment.Center),
                )

                else -> {
                    val activeTargetId = state.targets.firstOrNull { it.unlocked && it.status == 0 }?.id
                    LazyColumn(
                        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 24.dp),
                    ) {
                        item {
                            LessonHeaderCard(
                                lesson = lesson,
                                modifier = Modifier.padding(bottom = 16.dp),
                            )
                        }
                        itemsIndexed(state.targets, key = { _, target -> target.id }) { index, target ->
                            TargetTimelineRow(
                                target = target,
                                isActive = target.id == activeTargetId,
                                isLast = index == state.targets.lastIndex,
                                onClick = {},
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TargetsScreenPreview() {
    KidsDemoTheme {
        TargetsScreen(
            state = SyllabusContract.State(
                openedLesson = SyllabusLesson(
                    id = 6918,
                    title = "Letters ( ج jeem, ح ḥāʾ, خ khāʾ )",
                    icon = "",
                    order = 2,
                    slug = "A0_Unit1_Lesson2",
                    estimate = 16,
                    isRevise = false,
                    levelId = 345,
                    status = 0,
                ),
                targets = listOf(
                    SyllabusTarget(id = 1, title = "Section title", type = "Vocabulary", slug = "", order = 1, icon = "", unlocked = true, lessonId = 6918, levelId = 345, status = 1),
                    SyllabusTarget(id = 2, title = "Section title", type = "Vocabulary", slug = "", order = 2, icon = "", unlocked = true, lessonId = 6918, levelId = 345, status = 0),
                    SyllabusTarget(id = 3, title = "Section title", type = "Vocabulary", slug = "", order = 3, icon = "", unlocked = true, lessonId = 6918, levelId = 345, status = 0),
                    SyllabusTarget(id = 4, title = "Section title", type = "Vocabulary", slug = "", order = 4, icon = "", unlocked = false, lessonId = 6918, levelId = 345, status = 0),
                ),
                fetchState = SyllabusContract.FetchState.LoadData(),
            ),
            onEventSent = {},
            onCloseClicked = {},
        )
    }
}
