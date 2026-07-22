package com.alifbee.kidsdemo.ui.screens.homescreen.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alifbee.kidsdemo.R
import com.alifbee.kidsdemo.data.model.Course
import com.alifbee.kidsdemo.ui.theme.KidsDemoTheme
import com.alifbee.kidsdemo.ui.theme.LocalCustomColors

@Composable
internal fun CurrentCourseSection(
    course: Course,
    onCourseClicked: () -> Unit,
    onSeeAllClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = stringResource(R.string.your_course),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 20.sp,
            modifier = Modifier.padding(bottom = 8.dp),
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(LocalCustomColors.current.courseCardBg)
                .clickable(onClick = onCourseClicked)
                .padding(16.dp),
        ) {
            Row {
                Image(
                    painter = painterResource(R.drawable.ic_courses),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(64.dp),
                )
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 16.dp),
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = stringResource(R.string.current_course),
                            color = MaterialTheme.colorScheme.onBackground,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.weight(1f),
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .clickable(onClick = onSeeAllClicked)
                                .padding(vertical = 4.dp),
                        ) {
                            Text(
                                text = stringResource(R.string.see_all),
                                color = MaterialTheme.colorScheme.onBackground,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                            )
                            Image(
                                painter = painterResource(R.drawable.ic_back),
                                contentDescription = null,
                                contentScale = ContentScale.Fit,
                                modifier = Modifier
                                    .padding(start = 8.dp)
                                    .size(16.dp),
                            )
                        }
                    }
                    Text(
                        text = course.title,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(top = 4.dp),
                    )
                }
            }
            Text(
                text = course.level,
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(top = 16.dp),
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
                    .height(4.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(LocalCustomColors.current.courseProgressTrack),
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(course.progressPercent.coerceIn(0, 100) / 100f)
                        .fillMaxHeight()
                        .background(
                            LocalCustomColors.current.brandYellow,
                            RoundedCornerShape(4.dp),
                        ),
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CurrentCourseSectionPreview() {
    KidsDemoTheme {
        CurrentCourseSection(
            course = Course(
                id = "1",
                title = "Arabic Mastery",
                level = "A0 - Beginner",
                progressPercent = 40,
            ),
            onCourseClicked = {},
            onSeeAllClicked = {},
        )
    }
}
