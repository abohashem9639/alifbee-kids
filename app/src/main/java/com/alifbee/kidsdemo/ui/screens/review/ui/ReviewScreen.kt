package com.alifbee.kidsdemo.ui.screens.review.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alifbee.kidsdemo.R
import com.alifbee.kidsdemo.data.model.ReviewGroup
import com.alifbee.kidsdemo.data.model.ReviewItem
import com.alifbee.kidsdemo.ui.common.CollectEffect
import com.alifbee.kidsdemo.ui.screens.review.ui.components.CollapsibleReviewHeader
import com.alifbee.kidsdemo.ui.screens.review.ui.components.ReviewEntryRow
import com.alifbee.kidsdemo.ui.screens.review.ui.components.ReviewGroupHeader
import com.alifbee.kidsdemo.ui.screens.review.ui.viewmodel.ReviewContract
import com.alifbee.kidsdemo.ui.screens.review.ui.viewmodel.loading
import com.alifbee.kidsdemo.ui.theme.KidsDemoTheme
import com.alifbee.kidsdemo.ui.theme.LocalCustomColors
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Composable
internal fun ReviewScreen(
    state: ReviewContract.State,
    effectFlow: Flow<ReviewContract.Effect>,
    onEventSent: (event: ReviewContract.Event) -> Unit,
) {
    effectFlow.CollectEffect { }

    ReviewContent(
        state = state,
        onEventSent = onEventSent,
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ReviewContent(
    state: ReviewContract.State,
    onEventSent: (event: ReviewContract.Event) -> Unit,
) {
    val collapseRangePx = with(LocalDensity.current) { 116.dp.toPx() }
    var headerScrollOffset by remember { mutableFloatStateOf(0f) }
    val collapseFraction = if (collapseRangePx == 0f) 0f else (-headerScrollOffset / collapseRangePx).coerceIn(0f, 1f)

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                if (available.y >= 0) return Offset.Zero
                val newOffset = (headerScrollOffset + available.y).coerceIn(-collapseRangePx, 0f)
                val consumed = newOffset - headerScrollOffset
                headerScrollOffset = newOffset
                return Offset(0f, consumed)
            }

            override fun onPostScroll(
                consumed: Offset,
                available: Offset,
                source: NestedScrollSource,
            ): Offset {
                if (available.y <= 0) return Offset.Zero
                val newOffset = (headerScrollOffset + available.y).coerceIn(-collapseRangePx, 0f)
                val consumedByHeader = newOffset - headerScrollOffset
                headerScrollOffset = newOffset
                return Offset(0f, consumedByHeader)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(LocalCustomColors.current.plumPurple)
                .statusBarsPadding()
                .height(56.dp),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = stringResource(R.string.review_title),
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
            )
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .nestedScroll(nestedScrollConnection),
        ) {
            CollapsibleReviewHeader(
                countText = if (state.fetchState.loading) "" else state.itemsReadyCount.toString(),
                collapseFraction = collapseFraction,
                onReviewAllClicked = { onEventSent(ReviewContract.Event.OnReviewAllClicked) },
                onInfoClicked = { onEventSent(ReviewContract.Event.OnInfoClicked) },
            )
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 24.dp),
            ) {
                state.groups.forEach { group ->
                    stickyHeader {
                        ReviewGroupHeader(title = group.title)
                    }
                    items(group.items) { reviewItem ->
                        ReviewEntryRow(
                            item = reviewItem,
                            onItemClicked = { onEventSent(ReviewContract.Event.OnItemClicked(reviewItem)) },
                            onAudioClicked = { onEventSent(ReviewContract.Event.OnItemAudioClicked(reviewItem)) },
                            onBookmarkClicked = { onEventSent(ReviewContract.Event.OnItemBookmarkClicked(reviewItem)) },
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ReviewScreenPreview() {
    KidsDemoTheme {
        ReviewScreen(
            state = ReviewContract.State(
                itemsReadyCount = 38,
                groups = listOf(
                    ReviewGroup(
                        id = "words",
                        title = "Items (24)",
                        items = listOf(
                            ReviewItem(id = "w1", arabic = "يتحرك", english = "Move", iconRes = R.drawable.ic_alifbee_2, isBookmarked = false),
                            ReviewItem(id = "w2", arabic = "سحابة السماء", english = "Cloud sky", iconRes = R.drawable.ic_alifbee_2, isBookmarked = true),
                        ),
                    ),
                ),
                fetchState = ReviewContract.FetchState.LoadData(),
            ),
            effectFlow = emptyFlow(),
            onEventSent = {},
        )
    }
}
