package com.alifbee.kidsdemo.ui.screens.levels.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Layers
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.alifbee.kidsdemo.core.designsystem.R
import com.alifbee.kidsdemo.data.model.SyllabusCourse
import com.alifbee.kidsdemo.ui.theme.KidsDemoTheme
import com.alifbee.kidsdemo.ui.theme.LocalCustomColors
import com.alifbee.kidsdemo.ui.theme.moreByUsBase

@Composable
internal fun CourseHeaderCard(
    course: SyllabusCourse,
    levelsCount: Int,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .background(LocalCustomColors.current.courseCardBg, RoundedCornerShape(8.dp))
            .padding(horizontal = 16.dp, vertical = 12.dp),
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(course.icon)
                .crossfade(true)
                .build(),
            contentDescription = null,
            placeholder = painterResource(R.drawable.ic_courses),
            error = painterResource(R.drawable.ic_courses),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .border(2.dp, moreByUsBase, CircleShape),
        )
        Column(modifier = Modifier.padding(start = 12.dp)) {
            Text(
                text = course.title,
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 4.dp),
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_levels_3),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.size(24.dp),
                )
                Text(
                    text = stringResource(R.string.levels_count_format, levelsCount),
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(start = 6.dp),
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CourseHeaderCardPreview() {
    KidsDemoTheme {
        CourseHeaderCard(
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
            levelsCount = 3,
        )
    }
}
