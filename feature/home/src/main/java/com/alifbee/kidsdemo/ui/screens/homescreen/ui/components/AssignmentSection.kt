package com.alifbee.kidsdemo.ui.screens.homescreen.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alifbee.kidsdemo.core.designsystem.R
import com.alifbee.kidsdemo.data.model.Assignment
import com.alifbee.kidsdemo.data.model.AssignmentStatus
import com.alifbee.kidsdemo.ui.theme.KidsDemoTheme
import com.alifbee.kidsdemo.ui.theme.LocalCustomColors

@Composable
internal fun AssignmentSection(
    assignment: Assignment,
    onAssignmentClicked: () -> Unit,
    onAssignmentStatusClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val ringColor = when (assignment.status) {
        AssignmentStatus.TO_DO -> LocalCustomColors.current.statusTodo
        AssignmentStatus.DONE -> LocalCustomColors.current.statusDone
        AssignmentStatus.OVERDUE -> MaterialTheme.colorScheme.error
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onAssignmentClicked)
            .padding(8.dp),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            AssignmentProgressRing(
                targetProgress = assignment.progressPercent,
                ringColor = ringColor,
            )
            Text(
                text = assignment.title,
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 12.dp),
            )
            Image(
                painter = painterResource(R.drawable.ic_back),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(16.dp),
            )
        }

        Row(modifier = Modifier.padding(top = 24.dp)) {
            Column(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .width(80.dp),
            ) {
                AssignmentLabel(text = stringResource(R.string.assignment_type))
                AssignmentLabel(text = stringResource(R.string.assignment_due_date))
                AssignmentLabel(text = stringResource(R.string.assignment_classroom))
                AssignmentLabel(text = stringResource(R.string.assignment_lessons))
                AssignmentLabel(text = stringResource(R.string.assignment_level))
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 32.dp),
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 4.dp),
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_quize),
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.size(18.dp),
                    )
                    Text(
                        text = assignment.type,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(start = 6.dp),
                    )
                }
                AssignmentValue(text = assignment.dueDate)
                AssignmentValue(text = assignment.classroom)
                AssignmentValue(text = assignment.lessonsProgress)
                AssignmentValue(text = assignment.level)
            }
        }

        AssignmentStatusBadge(
            status = assignment.status,
            onClick = onAssignmentStatusClicked,
            modifier = Modifier.padding(top = 16.dp),
        )
    }
}

@Composable
private fun AssignmentProgressRing(
    targetProgress: Int,
    ringColor: Color,
) {
    var animationStarted by remember(targetProgress) { mutableStateOf(false) }
    val progress = remember(targetProgress) { Animatable(0f) }

    LaunchedEffect(animationStarted) {
        if (animationStarted) {
            progress.animateTo(
                targetValue = targetProgress.coerceIn(0, 100).toFloat(),
                animationSpec = tween(1500, easing = LinearOutSlowInEasing),
            )
        }
    }

    Box(
        modifier = Modifier
            .size(64.dp)
            .onGloballyPositioned { coordinates ->
                if (!animationStarted && coordinates.boundsInWindow().height > 0f) {
                    animationStarted = true
                }
            },
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator(
            progress = { progress.value / 100f },
            color = ringColor,
            trackColor = LocalCustomColors.current.assignmentTrack,
            strokeWidth = 6.dp,
            strokeCap = StrokeCap.Round,
            gapSize = 0.dp,
            modifier = Modifier.size(56.dp),
        )
        Text(
            text = stringResource(R.string.percent_format, progress.value.toInt()),
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Composable
private fun AssignmentLabel(text: String) {
    Text(
        text = text,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        fontSize = 14.sp,
        modifier = Modifier.padding(vertical = 4.dp),
    )
}

@Composable
private fun AssignmentValue(text: String) {
    Text(
        text = text,
        color = MaterialTheme.colorScheme.onBackground,
        fontSize = 14.sp,
        modifier = Modifier.padding(vertical = 4.dp),
    )
}

@Composable
private fun AssignmentStatusBadge(
    status: AssignmentStatus,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val (badgeColor, labelRes) = when (status) {
        AssignmentStatus.TO_DO -> LocalCustomColors.current.statusTodo to R.string.status_to_do
        AssignmentStatus.DONE -> LocalCustomColors.current.statusDone to R.string.status_done
        AssignmentStatus.OVERDUE -> MaterialTheme.colorScheme.error to R.string.status_overdue
    }
    val shape = RoundedCornerShape(6.dp)

    Box(
        modifier = modifier
            .clip(shape)
            .background(badgeColor, shape)
            .clickable(enabled = status == AssignmentStatus.TO_DO, onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 6.dp),
    ) {
        Text(
            text = stringResource(labelRes),
            color = MaterialTheme.colorScheme.onPrimary,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AssignmentSectionPreview() {
    KidsDemoTheme {
        AssignmentSection(
            assignment = Assignment(
                id = "1",
                title = "Assignment title 1",
                progressPercent = 60,
                type = "Quiz",
                dueDate = "Dec 9, 2021",
                classroom = "C1",
                lessonsProgress = "2/24",
                level = "Elementary",
                status = AssignmentStatus.TO_DO,
            ),
            onAssignmentClicked = {},
            onAssignmentStatusClicked = {},
        )
    }
}
