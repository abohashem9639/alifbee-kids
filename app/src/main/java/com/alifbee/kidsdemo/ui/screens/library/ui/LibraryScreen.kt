package com.alifbee.kidsdemo.ui.screens.library.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alifbee.kidsdemo.R
import com.alifbee.kidsdemo.data.model.SyllabusLesson
import com.alifbee.kidsdemo.data.model.SyllabusLevel
import com.alifbee.kidsdemo.ui.screens.library.ui.components.LessonTimelineRow
import com.alifbee.kidsdemo.ui.screens.library.ui.components.LevelSelector
import com.alifbee.kidsdemo.ui.screens.library.ui.components.LibrarySkeleton
import com.alifbee.kidsdemo.ui.screens.library.ui.components.ReviewTimelineRow
import com.alifbee.kidsdemo.ui.screens.library.ui.viewmodel.SyllabusContract
import com.alifbee.kidsdemo.ui.screens.library.ui.viewmodel.loading
import com.alifbee.kidsdemo.ui.theme.KidsDemoTheme

private sealed interface LibraryListEntry {
    data class Lesson(val lesson: SyllabusLesson, val number: Int) : LibraryListEntry
    data class Review(val lesson: SyllabusLesson) : LibraryListEntry
}

@Composable
internal fun LibraryScreen(
    state: SyllabusContract.State,
    onEventSent: (event: SyllabusContract.Event) -> Unit,
    onLevelSelectorClicked: () -> Unit,
    onLessonClicked: (SyllabusLesson) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .statusBarsPadding(),
    ) {
        Text(
            text = stringResource(R.string.library_title),
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp),
        )

        Box(modifier = Modifier.weight(1f)) {
            when {
                state.fetchState.loading -> LibrarySkeleton()

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

                else -> {
                    val entries = remember(state.lessons) { state.lessons.toListEntries() }
                    LazyColumn(
                        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 24.dp),
                    ) {
                        item {
                            LevelSelector(
                                selectedLevel = state.selectedLevel,
                                completedCount = state.completedCount,
                                totalCount = state.lessons.size,
                                onClick = onLevelSelectorClicked,
                                modifier = Modifier.padding(bottom = 16.dp),
                            )
                        }
                        items(entries) { entry ->
                            when (entry) {
                                is LibraryListEntry.Lesson -> LessonTimelineRow(
                                    lesson = entry.lesson,
                                    lessonNumber = entry.number,
                                    isLast = entry == entries.lastOrNull(),
                                    onClick = { onLessonClicked(entry.lesson) },
                                )
                                is LibraryListEntry.Review -> ReviewTimelineRow(
                                    onClick = { onLessonClicked(entry.lesson) },
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

private fun List<SyllabusLesson>.toListEntries(): List<LibraryListEntry> = buildList {
    var counter = 0
    this@toListEntries.forEach { lesson ->
        if (lesson.isRevise) {
            add(LibraryListEntry.Review(lesson))
            counter = 0
        } else {
            counter++
            add(LibraryListEntry.Lesson(lesson, counter))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LibraryScreenPreview() {
    KidsDemoTheme {
        LibraryScreen(
            state = SyllabusContract.State(
                levels = listOf(
                    SyllabusLevel(id = 345, slug = "A0", title = "A0 - Beginner", description = "", order = 95),
                ),
                selectedLevel = SyllabusLevel(id = 345, slug = "A0", title = "A0 - Beginner", description = "", order = 95),
                lessons = listOf(
                    SyllabusLesson(id = 1, title = "Letters (ب bāʾ, ت tāʾ, ث thāʾ)", icon = "", order = 1, slug = "A0_Unit1_Lesson1", estimate = 17, isRevise = false, levelId = 345, status = 1),
                    SyllabusLesson(id = 2, title = "Letters (ج jeem, ح ḥāʾ, خ khāʾ)", icon = "", order = 2, slug = "A0_Unit1_Lesson2", estimate = 16, isRevise = true, levelId = 345, status = 0),
                    SyllabusLesson(id = 3, title = "Letters (م meem, ن nūn, هـ hāʾ)", icon = "", order = 3, slug = "A0_Unit1_Lesson3", estimate = 18, isRevise = false, levelId = 345, status = 0),
                ),
                completedCount = 1,
                fetchState = SyllabusContract.FetchState.LoadData(),
            ),
            onEventSent = {},
            onLevelSelectorClicked = {},
            onLessonClicked = {},
        )
    }
}
