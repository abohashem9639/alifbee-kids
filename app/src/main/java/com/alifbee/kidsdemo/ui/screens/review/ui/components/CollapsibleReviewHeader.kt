package com.alifbee.kidsdemo.ui.screens.review.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import com.alifbee.kidsdemo.R
import com.alifbee.kidsdemo.ui.theme.KidsDemoTheme
import com.alifbee.kidsdemo.ui.theme.LocalCustomColors

@Composable
internal fun CollapsibleReviewHeader(
    countText: String,
    collapseFraction: Float,
    onReviewAllClicked: () -> Unit,
    onInfoClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val headerHeight = lerp(180.dp, 64.dp, collapseFraction)
    val countFontSize = lerp(36.sp, 20.sp, collapseFraction)
    val subtitleFontSize = lerp(14.sp, 12.sp, collapseFraction)
    val buttonFontSize = lerp(20.sp, 14.sp, collapseFraction)
    val buttonHeight = lerp(52.dp, 36.dp, collapseFraction)

    Layout(
        content = {
            Text(
                text = countText,
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = countFontSize,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = stringResource(R.string.review_items_ready_for_review),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = subtitleFontSize,
            )
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(LocalCustomColors.current.brandYellow)
                    .clickable(onClick = onReviewAllClicked),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = stringResource(R.string.review_all),
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = buttonFontSize,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 16.dp),
                )
            }
            Image(
                painter = painterResource(R.drawable.ic_info_outline),
                contentDescription = stringResource(R.string.cd_info),
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(24.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = onInfoClicked,
                    ),
            )
        },
        modifier = modifier
            .fillMaxWidth()
            .height(headerHeight)
            .clipToBounds(),
    ) { measurables, constraints ->
        val width = constraints.maxWidth
        val height = constraints.maxHeight
        val padding = 16.dp.roundToPx()
        val buttonHeightPx = buttonHeight.roundToPx()

        val count = measurables[0].measure(Constraints())
        val subtitle = measurables[1].measure(Constraints())
        val info = measurables[3].measure(Constraints())

        val expandedButtonWidth = width - padding * 2
        val collapsedButtonWidth = measurables[2].maxIntrinsicWidth(buttonHeightPx)
        val buttonWidth = lerp(expandedButtonWidth, collapsedButtonWidth, collapseFraction)
        val button = measurables[2].measure(Constraints.fixed(buttonWidth, buttonHeightPx))

        val countYExpanded = padding
        val subtitleYExpanded = countYExpanded + count.height
        val collapsedBlockHeight = count.height + 4.dp.roundToPx() + subtitle.height
        val blockTopCollapsed = ((height - collapsedBlockHeight) / 2).coerceAtLeast(0)
        val countYCollapsed = blockTopCollapsed
        val subtitleYCollapsed = blockTopCollapsed + count.height + 4.dp.roundToPx()

        val countY = lerp(countYExpanded, countYCollapsed, collapseFraction)
        val subtitleY = lerp(subtitleYExpanded, subtitleYCollapsed, collapseFraction)

        val buttonXExpanded = padding
        val buttonXCollapsed = width - padding - info.width - 8.dp.roundToPx() - button.width
        val buttonYExpanded = subtitleYExpanded + subtitle.height + padding
        val buttonYCollapsed = height - 14.dp.roundToPx() - button.height
        val buttonX = lerp(buttonXExpanded, buttonXCollapsed, collapseFraction)
        val buttonY = lerp(buttonYExpanded, buttonYCollapsed, collapseFraction)

        val infoX = width - padding - info.width
        val infoYExpanded = countYExpanded + (count.height - info.height) / 2
        val infoYCollapsed = height - 20.dp.roundToPx() - info.height
        val infoY = lerp(infoYExpanded, infoYCollapsed, collapseFraction)

        layout(width, height) {
            count.place(padding, countY)
            subtitle.place(padding, subtitleY)
            button.place(buttonX, buttonY)
            info.place(infoX, infoY)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CollapsibleReviewHeaderExpandedPreview() {
    KidsDemoTheme {
        CollapsibleReviewHeader(
            countText = "38",
            collapseFraction = 0f,
            onReviewAllClicked = {},
            onInfoClicked = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CollapsibleReviewHeaderCollapsedPreview() {
    KidsDemoTheme {
        CollapsibleReviewHeader(
            countText = "38",
            collapseFraction = 1f,
            onReviewAllClicked = {},
            onInfoClicked = {},
        )
    }
}
