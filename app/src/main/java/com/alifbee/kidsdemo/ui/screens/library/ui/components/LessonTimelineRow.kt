package com.alifbee.kidsdemo.ui.screens.library.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.alifbee.kidsdemo.R
import com.alifbee.kidsdemo.data.model.SyllabusLesson
import com.alifbee.kidsdemo.ui.theme.KidsDemoTheme
import com.alifbee.kidsdemo.ui.theme.LocalCustomColors

@Composable
internal fun LessonTimelineRow(
    lesson: SyllabusLesson,
    lessonNumber: Int,
    isLast: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.width(64.dp),
        ) {
            Box {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(lesson.icon)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    placeholder = painterResource(R.drawable.ic_alifbee),
                    error = painterResource(R.drawable.ic_alifbee),
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(48.dp),
                )
                if (lesson.status != 0) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .size(16.dp)
                            .background(MaterialTheme.colorScheme.secondary, CircleShape),
                        contentAlignment = Alignment.Center,
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSecondary,
                            modifier = Modifier.size(10.dp),
                        )
                    }
                }
            }
            if (!isLast) {
                Box(
                    modifier = Modifier
                        .padding(vertical = 4.dp)
                        .width(2.dp)
                        .height(24.dp)
                        .background(LocalCustomColors.current.dayEmptyStroke),
                )
            }
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp, bottom = 16.dp),
        ) {
            Text(
                text = stringResource(R.string.lesson_number_format, lessonNumber),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 12.sp,
            )
            Text(
                text = lesson.title,
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 2.dp),
            )
        }
    }
}

@Composable
internal fun ReviewTimelineRow(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val shape = RoundedCornerShape(12.dp)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
            .clickable(onClick = onClick),
    ) {
        Box(
            modifier = Modifier
                .matchParentSize()
                .padding(top = 4.dp)
                .background(LocalCustomColors.current.dayGlow, shape),
        )
        ReviewCardFace(shape = shape)
    }
}

@Composable
private fun ReviewCardFace(shape: RoundedCornerShape) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 4.dp)
            .background(LocalCustomColors.current.courseCardBg, shape)
            .border(1.5.dp, LocalCustomColors.current.dayGlow, shape)
            .padding(horizontal = 12.dp, vertical = 12.dp),
    ) {
        Image(
            painter = painterResource(R.drawable.img_bee),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier.size(40.dp),
        )
        Column(modifier = Modifier.padding(start = 12.dp)) {
            Text(
                text = stringResource(R.string.review_label),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 12.sp,
            )
            Text(
                text = stringResource(R.string.review_completed_lesson),
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 2.dp),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LessonTimelineRowPreview() {
    KidsDemoTheme {
        Column {
            LessonTimelineRow(
                lesson = SyllabusLesson(
                    id = 6917,
                    title = "Letters (ب bāʾ, ت tāʾ, ث thāʾ)",
                    icon = "",
                    order = 1,
                    slug = "A0_Unit1_Lesson1",
                    estimate = 17,
                    isRevise = false,
                    levelId = 345,
                    status = 1,
                ),
                lessonNumber = 1,
                isLast = false,
                onClick = {},
            )
            ReviewTimelineRow(onClick = {})
        }
    }
}
