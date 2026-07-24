package com.alifbee.kidsdemo.ui.screens.targets.ui.components

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alifbee.kidsdemo.R
import com.alifbee.kidsdemo.data.model.SyllabusTarget
import com.alifbee.kidsdemo.ui.theme.KidsDemoTheme
import com.alifbee.kidsdemo.ui.theme.LocalCustomColors

@Composable
internal fun TargetTimelineRow(
    target: SyllabusTarget,
    isActive: Boolean,
    isLast: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val isCompleted = target.status != 0

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(
                if (isActive) LocalCustomColors.current.statisticCardBg else MaterialTheme.colorScheme.background
            )
            .clickable(enabled = target.unlocked, onClick = onClick)
            .padding(horizontal = 8.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.width(28.dp),
        ) {
            HexagonStatusIndicator(isCompleted = isCompleted)
            if (!isLast) {
                Box(
                    modifier = Modifier
                        .padding(top = 6.dp)
                        .width(2.dp)
                        .height(24.dp)
                        .background(LocalCustomColors.current.brandPurple),
                )
            }
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp),
        ) {
            Text(
                text = target.type,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 12.sp,
            )
            Text(
                text = target.title,
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 2.dp),
            )
        }

        TargetActionIcon(isCompleted = isCompleted, isUnlocked = target.unlocked)
    }
}

@Composable
private fun HexagonStatusIndicator(isCompleted: Boolean) {
    Box(
        modifier = Modifier
            .size(20.dp)
            .background(
                if (isCompleted) MaterialTheme.colorScheme.secondary else LocalCustomColors.current.dayEmptyStroke,
                HexagonShape,
            ),
        contentAlignment = Alignment.Center,
    ) {
        if (isCompleted) {
            Icon(
                imageVector = Icons.Filled.Check,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSecondary,
                modifier = Modifier.size(12.dp),
            )
        }
    }
}

@Composable
private fun TargetActionIcon(isCompleted: Boolean, isUnlocked: Boolean) {
    when {
        isCompleted -> Icon(
            imageVector = Icons.Outlined.Refresh,
            contentDescription = stringResource(R.string.cd_target_replay),
            tint = LocalCustomColors.current.brandPurple,
            modifier = Modifier.size(24.dp),
        )
        isUnlocked -> Icon(
            imageVector = Icons.Filled.PlayArrow,
            contentDescription = stringResource(R.string.cd_target_play),
            tint = LocalCustomColors.current.brandPurple,
            modifier = Modifier.size(24.dp),
        )
        else -> Icon(
            imageVector = Icons.Outlined.Lock,
            contentDescription = stringResource(R.string.cd_target_locked),
            tint = LocalCustomColors.current.brandPurple,
            modifier = Modifier.size(24.dp),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TargetTimelineRowPreview() {
    KidsDemoTheme {
        Column {
            TargetTimelineRow(
                target = SyllabusTarget(id = 1, title = "Section title", type = "Vocabulary", slug = "", order = 1, icon = "", unlocked = true, lessonId = 1, levelId = 1, status = 1),
                isActive = false,
                isLast = false,
                onClick = {},
            )
            TargetTimelineRow(
                target = SyllabusTarget(id = 2, title = "Section title", type = "Vocabulary", slug = "", order = 2, icon = "", unlocked = true, lessonId = 1, levelId = 1, status = 0),
                isActive = true,
                isLast = false,
                onClick = {},
            )
            TargetTimelineRow(
                target = SyllabusTarget(id = 3, title = "Section title", type = "Vocabulary", slug = "", order = 3, icon = "", unlocked = false, lessonId = 1, levelId = 1, status = 0),
                isActive = false,
                isLast = true,
                onClick = {},
            )
        }
    }
}
