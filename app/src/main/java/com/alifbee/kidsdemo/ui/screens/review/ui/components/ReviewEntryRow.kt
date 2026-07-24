package com.alifbee.kidsdemo.ui.screens.review.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alifbee.kidsdemo.R
import com.alifbee.kidsdemo.data.model.ReviewItem
import com.alifbee.kidsdemo.ui.theme.KidsDemoTheme
import com.alifbee.kidsdemo.ui.theme.LocalCustomColors

@Composable
internal fun ReviewEntryRow(
    item: ReviewItem,
    onItemClicked: () -> Unit,
    onAudioClicked: () -> Unit,
    onBookmarkClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .clickable(onClick = onItemClicked)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(end = 16.dp),
        ) {
            Text(
                text = item.text,
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .height(1.dp)
                    .background(LocalCustomColors.current.reviewItemDivider),
            )
            if (!item.translation.isNullOrBlank()) {
                Text(
                    text = item.translation,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }

        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(LocalCustomColors.current.brandYellow)
                .clickable(onClick = onAudioClicked)
                .padding(8.dp),
            contentAlignment = Alignment.Center,
        ) {
            Image(
                painter = painterResource(R.drawable.ic_audio),
                contentDescription = null,
                contentScale = ContentScale.Fit,
            )
        }

        Image(
            painter = painterResource(
                if (item.isFavorite) R.drawable.ic_bookmark_filled else R.drawable.ic_bookmark_outline
            ),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .padding(start = 8.dp)
                .size(24.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = onBookmarkClicked,
                ),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ReviewEntryRowPreview() {
    KidsDemoTheme {
        ReviewEntryRow(
            item = ReviewItem(
                id = 1,
                text = "يتحرك",
                translation = "Move",
                sound = null,
                score = 2,
                isFavorite = false,
                levelId = 1,
                lessonId = 5,
                lessonSlug = "A1_Unit1_Lesson1",
            ),
            onItemClicked = {},
            onAudioClicked = {},
            onBookmarkClicked = {},
        )
    }
}
