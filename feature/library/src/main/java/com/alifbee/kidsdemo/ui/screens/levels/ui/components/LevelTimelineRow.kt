package com.alifbee.kidsdemo.ui.screens.levels.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alifbee.kidsdemo.core.designsystem.R
import com.alifbee.kidsdemo.data.model.SyllabusLevel
import com.alifbee.kidsdemo.ui.screens.library.ui.viewmodel.SyllabusContract
import com.alifbee.kidsdemo.ui.theme.KidsDemoTheme
import com.alifbee.kidsdemo.ui.theme.LocalCustomColors

@Composable
internal fun LevelTimelineRow(
    item: SyllabusContract.LevelItem,
    isLast: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val isCompleted = item.progress >= 1f
    val isInProgress = item.progress > 0f && item.progress < 1f

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(
                if (isInProgress) LocalCustomColors.current.courseCardBg else MaterialTheme.colorScheme.background
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 8.dp, vertical = 8.dp),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.width(32.dp),
        ) {
            LevelStatusIndicator(isCompleted = isCompleted)
            if (!isLast) {
                Box(
                    modifier = Modifier
                        .padding(top = 6.dp)
                        .width(2.dp)
                        .height(28.dp)
                        .background(LocalCustomColors.current.dayEmptyStroke),
                )
            }
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp),
        ) {
            Text(
                text = item.level.title,
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 4.dp),
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_lesson),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    colorFilter = ColorFilter.tint(LocalCustomColors.current.brandPurple),
                    modifier = Modifier.size(24.dp),
                )
                Text(
                    text = stringResource(R.string.lessons_count_format, item.lessonsCount),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(start = 6.dp),
                )
            }
        }

        if (item.progress > 0f) {
            CircularProgressIndicator(
                progress = { item.progress },
                color = if (isCompleted) LocalCustomColors.current.statusDone else LocalCustomColors.current.statusTodo,
                trackColor = LocalCustomColors.current.assignmentTrack,
                strokeWidth = 3.dp,
                strokeCap = StrokeCap.Round,
                gapSize = 0.dp,
                modifier = Modifier
                    .padding(start = 8.dp)
                    .size(28.dp)
                    .align(Alignment.CenterVertically),
            )
        }
    }
}

@Composable
private fun LevelStatusIndicator(isCompleted: Boolean) {
    if (isCompleted) {
        Box(
            modifier = Modifier
                .size(20.dp)
                .background(MaterialTheme.colorScheme.secondary, CircleShape),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = Icons.Filled.Check,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSecondary,
                modifier = Modifier.size(12.dp),
            )
        }
    } else {
        Box(
            modifier = Modifier
                .size(20.dp)
                .background(LocalCustomColors.current.dayEmptyStroke, CircleShape),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun LevelTimelineRowPreview() {
    KidsDemoTheme {
        Column {
            LevelTimelineRow(
                item = SyllabusContract.LevelItem(
                    level = SyllabusLevel(id = 345, slug = "A0", title = "A0 - Beginner", description = "", order = 95),
                    lessonsCount = 37,
                    progress = 1f,
                ),
                isLast = false,
                onClick = {},
            )
            LevelTimelineRow(
                item = SyllabusContract.LevelItem(
                    level = SyllabusLevel(id = 347, slug = "A2", title = "A2 - Novice mid", description = "", order = 97),
                    lessonsCount = 37,
                    progress = 0.4f,
                ),
                isLast = true,
                onClick = {},
            )
        }
    }
}
