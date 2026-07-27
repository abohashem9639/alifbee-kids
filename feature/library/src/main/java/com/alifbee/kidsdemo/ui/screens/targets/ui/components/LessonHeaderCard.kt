package com.alifbee.kidsdemo.ui.screens.targets.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.Image
import com.alifbee.kidsdemo.core.designsystem.R
import com.alifbee.kidsdemo.data.model.SyllabusLesson
import com.alifbee.kidsdemo.ui.theme.KidsDemoTheme
import com.alifbee.kidsdemo.ui.theme.LocalCustomColors
import com.alifbee.kidsdemo.ui.utils.sharedBoundsForNav
import com.alifbee.kidsdemo.ui.utils.sharedElementForNav

@Composable
internal fun LessonHeaderCard(
    lesson: SyllabusLesson,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .sharedBoundsForNav(key = "lesson-container-${lesson.id}")
            .background(LocalCustomColors.current.courseCardBg, RoundedCornerShape(8.dp))
            .padding(horizontal = 16.dp, vertical = 12.dp),
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .sharedElementForNav(key = "lesson-icon-${lesson.id}")
                .border(2.dp, LocalCustomColors.current.brandPurple, HexagonShape),
            contentAlignment = Alignment.Center,
        ) {
            Image(
                painter = painterResource(R.drawable.ic_lesson),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                colorFilter = ColorFilter.tint(LocalCustomColors.current.brandPurple),
                modifier = Modifier.size(20.dp),
            )
        }
        Column(modifier = Modifier.padding(start = 12.dp)) {
            Text(
                text = stringResource(R.string.lesson_number_format, lesson.order),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 12.sp,
            )
            Text(
                text = lesson.title,
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(top = 2.dp)
                    .sharedElementForNav(key = "lesson-title-${lesson.id}"),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LessonHeaderCardPreview() {
    KidsDemoTheme {
        LessonHeaderCard(
            lesson = SyllabusLesson(
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
        )
    }
}
