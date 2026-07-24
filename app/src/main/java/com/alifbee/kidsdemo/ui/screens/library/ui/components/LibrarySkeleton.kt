package com.alifbee.kidsdemo.ui.screens.library.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alifbee.kidsdemo.ui.common.ShimmerBox
import com.alifbee.kidsdemo.ui.common.shimmerBrush
import com.alifbee.kidsdemo.ui.theme.KidsDemoTheme

@Composable
internal fun LibrarySkeleton(modifier: Modifier = Modifier) {
    val brush = shimmerBrush()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
    ) {
        ShimmerBox(
            brush = brush,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(75.dp),
        )
        repeat(7) {
            Row(modifier = Modifier.padding(top = 16.dp)) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.width(64.dp),
                ) {
                    ShimmerBox(
                        brush = brush,
                        shape = CircleShape,
                        modifier = Modifier.size(48.dp),
                    )
                }
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp),
                ) {
                    ShimmerBox(
                        brush = brush,
                        modifier = Modifier
                            .width(60.dp)
                            .height(12.dp),
                    )
                    ShimmerBox(
                        brush = brush,
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .width(200.dp)
                            .height(16.dp),
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LibrarySkeletonPreview() {
    KidsDemoTheme {
        LibrarySkeleton()
    }
}
