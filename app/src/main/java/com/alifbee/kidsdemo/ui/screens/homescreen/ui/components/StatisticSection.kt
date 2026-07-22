package com.alifbee.kidsdemo.ui.screens.homescreen.ui.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alifbee.kidsdemo.R
import com.alifbee.kidsdemo.data.model.DayState
import com.alifbee.kidsdemo.data.model.DayStatus
import com.alifbee.kidsdemo.data.model.StreakStats
import com.alifbee.kidsdemo.data.model.WeeklyProgress
import com.alifbee.kidsdemo.ui.theme.KidsDemoTheme
import com.alifbee.kidsdemo.ui.theme.LocalCustomColors

@Composable
internal fun StatisticSection(
    weeklyProgress: WeeklyProgress,
    streakStats: StreakStats,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = stringResource(R.string.statistic),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 20.sp,
            modifier = Modifier.padding(bottom = 16.dp),
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(LocalCustomColors.current.statisticCardBg, RoundedCornerShape(16.dp))
                .padding(horizontal = 16.dp, vertical = 24.dp),
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(R.drawable.ic_black_lightning),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(32.dp),
                )
                Text(
                    text = stringResource(R.string.days_in_a_row),
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp),
                )
                Box(
                    modifier = Modifier
                        .defaultMinSize(minWidth = 43.dp)
                        .background(LocalCustomColors.current.dayGlow, RoundedCornerShape(22.dp))
                        .padding(4.dp)
                        .background(LocalCustomColors.current.brandYellow, RoundedCornerShape(18.dp))
                        .padding(horizontal = 12.dp, vertical = 4.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = streakStats.currentStreak.toString(),
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }

            Row(modifier = Modifier.padding(top = 16.dp)) {
                weeklyProgress.days.forEach { day ->
                    DayCell(
                        day = day,
                        modifier = Modifier.weight(1f),
                    )
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
                    .height(1.dp)
                    .background(MaterialTheme.colorScheme.outline),
            )

            Row(modifier = Modifier.padding(top = 24.dp)) {
                StreakStatItem(
                    iconRes = R.drawable.ic_calendar_2,
                    value = streakStats.activeDays,
                    labelRes = R.string.active_days,
                    modifier = Modifier.weight(1f),
                )
                StreakStatItem(
                    iconRes = R.drawable.ic_medal_2,
                    value = streakStats.longestStreak,
                    labelRes = R.string.longest_streak,
                    modifier = Modifier.weight(1f),
                )
            }
        }
    }
}

@Composable
private fun DayCell(
    day: DayStatus,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.TopCenter,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = if (day.isCurrent) {
                Modifier.background(LocalCustomColors.current.dayCurrentBg, RoundedCornerShape(24.dp))
            } else {
                Modifier
            }.padding(vertical = 8.dp),
        ) {
            Text(
                text = day.label,
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 14.sp,
                fontWeight = if (day.isCurrent) FontWeight.Bold else FontWeight.Normal,
            )
            DayIndicator(
                state = day.state,
                modifier = Modifier.padding(top = 8.dp),
            )
        }
    }
}

@Composable
private fun DayIndicator(
    state: DayState,
    modifier: Modifier = Modifier,
) {
    when (state) {
        DayState.COMPLETED -> FilledDayIndicator(
            glowColor = LocalCustomColors.current.dayGlow,
            fillColor = LocalCustomColors.current.brandYellow,
            modifier = modifier,
        )
        DayState.MISSED -> FilledDayIndicator(
            glowColor = LocalCustomColors.current.dayMissedGlow,
            fillColor = LocalCustomColors.current.dayMissedBg,
            modifier = modifier,
        )
        DayState.PENDING -> Box(
            modifier = modifier
                .size(36.dp)
                .border(2.dp, LocalCustomColors.current.dayEmptyStroke, CircleShape),
        )
    }
}

@Composable
private fun FilledDayIndicator(
    glowColor: Color,
    fillColor: Color,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .size(36.dp)
            .background(glowColor, CircleShape)
            .padding(4.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(fillColor, CircleShape),
        )
        Image(
            painter = painterResource(R.drawable.ic_black_lightning),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .size(16.dp)
                .align(Alignment.Center),
        )
    }
}

@Composable
private fun StreakStatItem(
    @DrawableRes iconRes: Int,
    value: Int,
    @StringRes labelRes: Int,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = painterResource(iconRes),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier.size(56.dp),
        )
        Column(modifier = Modifier.padding(start = 8.dp)) {
            Text(
                text = value.toString(),
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = stringResource(labelRes),
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 14.sp,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun StatisticSectionPreview() {
    KidsDemoTheme {
        StatisticSection(
            weeklyProgress = WeeklyProgress(
                days = listOf(
                    DayStatus(label = "Mon", state = DayState.COMPLETED, isCurrent = false),
                    DayStatus(label = "Tue", state = DayState.COMPLETED, isCurrent = false),
                    DayStatus(label = "Wed", state = DayState.MISSED, isCurrent = false),
                    DayStatus(label = "Thu", state = DayState.COMPLETED, isCurrent = true),
                    DayStatus(label = "Fri", state = DayState.PENDING, isCurrent = false),
                    DayStatus(label = "Sat", state = DayState.PENDING, isCurrent = false),
                    DayStatus(label = "Sun", state = DayState.PENDING, isCurrent = false),
                ),
            ),
            streakStats = StreakStats(
                currentStreak = 88,
                activeDays = 178,
                longestStreak = 90,
            ),
        )
    }
}
