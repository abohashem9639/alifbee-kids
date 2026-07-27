package com.alifbee.kidsdemo.ui.screens.homescreen.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alifbee.kidsdemo.core.designsystem.R
import com.alifbee.kidsdemo.data.model.UserProfile
import com.alifbee.kidsdemo.data.model.UserStats
import com.alifbee.kidsdemo.ui.theme.KidsDemoTheme
import com.alifbee.kidsdemo.ui.theme.LocalCustomColors

@Composable
internal fun ProfileCard(
    profile: UserProfile,
    stats: UserStats,
    onProfileClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Image(
                painter = painterResource(R.drawable.img_background),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(24.dp)),
            )
            ProfileInfo(
                profile = profile,
                onProfileClicked = onProfileClicked,
            )
        }
        StatsCard(
            stats = stats,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .layout { measurable, constraints ->
                    val placeable = measurable.measure(constraints)
                    layout(placeable.width, placeable.height / 2) {
                        placeable.place(0, -placeable.height / 2)
                    }
                },
        )
    }
}

@Composable
private fun ProfileInfo(
    profile: UserProfile,
    onProfileClicked: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .clickable(onClick = onProfileClicked)
            .padding(bottom = 8.dp),
    ) {
        Image(
            painter = painterResource(R.drawable.img_bee),
            contentDescription = stringResource(R.string.cd_avatar),
            modifier = Modifier
                .padding(start = 16.dp, top = 16.dp)
                .size(88.dp),
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp, top = 16.dp, end = 16.dp),
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = stringResource(R.string.greeting_hi, profile.name),
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Text(
                        text = profile.email,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 14.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(top = 2.dp),
                    )
                }
                Image(
                    painter = painterResource(R.drawable.ic_back),
                    contentDescription = stringResource(R.string.cd_profile),
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(24.dp),
                )
            }
            if (profile.isPremium) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 4.dp),
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_lightning),
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.size(24.dp),
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = stringResource(R.string.premium_account),
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
        }
    }
}

@Composable
private fun StatsCard(
    stats: UserStats,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .background(LocalCustomColors.current.profileStatsBg, RoundedCornerShape(16.dp))
            .padding(16.dp),
    ) {
        Row {
            StatItem(
                value = stats.honey,
                labelRes = R.string.stat_honey,
                modifier = Modifier.weight(1f),
            )
            StatItem(
                value = stats.dailyStreak,
                labelRes = R.string.stat_daily_streak,
                modifier = Modifier.weight(1f),
            )
        }
        Row(modifier = Modifier.padding(top = 16.dp)) {
            StatItem(
                value = stats.lessons,
                labelRes = R.string.stat_lesson,
                modifier = Modifier.weight(1f),
            )
            StatItem(
                value = stats.words,
                labelRes = R.string.stat_words,
                modifier = Modifier.weight(1f),
            )
        }
    }
}

@Composable
private fun StatItem(
    value: Int,
    @StringRes labelRes: Int,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = painterResource(R.drawable.ic_honey),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier.size(36.dp),
        )
        Column(modifier = Modifier.padding(start = 8.dp)) {
            Text(
                text = value.toString(),
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = stringResource(labelRes),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 14.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ProfileCardPreview() {
    KidsDemoTheme {
        ProfileCard(
            profile = UserProfile(
                name = "lora",
                email = "alexmcqueen@gmail.com",
                avatarUrl = null,
                isPremium = true,
            ),
            stats = UserStats(
                honey = 59,
                dailyStreak = 3,
                lessons = 12,
                words = 48,
            ),
            onProfileClicked = {},
        )
    }
}
