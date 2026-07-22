package com.alifbee.kidsdemo.ui.screens.homescreen.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.alifbee.kidsdemo.ui.theme.KidsDemoTheme
import com.alifbee.kidsdemo.ui.theme.LocalCustomColors

@Composable
internal fun HomescreenSkeleton(modifier: Modifier = Modifier) {
    val brush = shimmerBrush()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .systemBarsPadding()
            .padding(bottom = 32.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            SkeletonBox(brush = brush, width = 90.dp, height = 18.dp)
            Spacer(modifier = Modifier.weight(1f))
            SkeletonBox(brush = brush, width = 40.dp, height = 40.dp, shape = CircleShape)
        }

        Box(modifier = Modifier.padding(horizontal = 16.dp)) {
            SkeletonBox(
                brush = brush,
                height = 180.dp,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth(),
            )
            Row(modifier = Modifier.padding(16.dp)) {
                SkeletonBox(brush = brush, width = 88.dp, height = 88.dp, shape = CircleShape)
                Column(modifier = Modifier.padding(start = 16.dp)) {
                    SkeletonBox(
                        brush = brush,
                        width = 160.dp,
                        height = 22.dp,
                        modifier = Modifier.padding(top = 4.dp),
                    )
                    SkeletonBox(
                        brush = brush,
                        width = 200.dp,
                        height = 14.dp,
                        modifier = Modifier.padding(top = 8.dp),
                    )
                    SkeletonBox(
                        brush = brush,
                        width = 120.dp,
                        height = 14.dp,
                        modifier = Modifier.padding(top = 8.dp),
                    )
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(top = 140.dp)
                    .background(brush, RoundedCornerShape(16.dp))
                    .padding(16.dp),
            ) {
                Row {
                    SkeletonBox(brush = brush, height = 40.dp, modifier = Modifier.weight(1f))
                    Spacer(modifier = Modifier.size(16.dp))
                    SkeletonBox(brush = brush, height = 40.dp, modifier = Modifier.weight(1f))
                }
                Row(modifier = Modifier.padding(top = 8.dp)) {
                    SkeletonBox(brush = brush, height = 40.dp, modifier = Modifier.weight(1f))
                    Spacer(modifier = Modifier.size(16.dp))
                    SkeletonBox(brush = brush, height = 40.dp, modifier = Modifier.weight(1f))
                }
            }
        }

        SkeletonBox(
            brush = brush,
            height = 72.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 24.dp),
        )

        SkeletonBox(
            brush = brush,
            width = 120.dp,
            height = 18.dp,
            modifier = Modifier
                .padding(start = 16.dp)
                .padding(top = 24.dp),
        )
        SkeletonBox(
            brush = brush,
            height = 140.dp,
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 8.dp),
        )

        SkeletonBox(
            brush = brush,
            width = 120.dp,
            height = 18.dp,
            modifier = Modifier
                .padding(start = 16.dp)
                .padding(top = 24.dp),
        )
        Row(
            modifier = Modifier
                .padding(start = 16.dp)
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            repeat(4) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    SkeletonBox(brush = brush, width = 76.dp, height = 76.dp, shape = CircleShape)
                    SkeletonBox(
                        brush = brush,
                        width = 60.dp,
                        height = 16.dp,
                        modifier = Modifier.padding(top = 8.dp),
                    )
                }
            }
        }

        SkeletonBox(
            brush = brush,
            width = 120.dp,
            height = 18.dp,
            modifier = Modifier
                .padding(start = 16.dp)
                .padding(top = 24.dp),
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            repeat(3) {
                SkeletonBox(
                    brush = brush,
                    height = 160.dp,
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.weight(1f),
                )
            }
        }

        SkeletonBox(
            brush = brush,
            height = 240.dp,
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 24.dp),
        )

        SkeletonBox(
            brush = brush,
            height = 220.dp,
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 24.dp),
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 32.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            repeat(3) {
                SkeletonBox(
                    brush = brush,
                    height = 220.dp,
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.weight(1f),
                )
            }
        }
    }
}

@Composable
private fun shimmerBrush(): Brush {
    val transition = rememberInfiniteTransition()
    val translate by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1400f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart,
        ),
    )
    val placeholder = LocalCustomColors.current.skeletonPlaceholder
    val highlight = LocalCustomColors.current.skeletonHighlight

    return Brush.linearGradient(
        colors = listOf(placeholder, highlight, placeholder),
        start = Offset(translate - 400f, 0f),
        end = Offset(translate, 0f),
    )
}

@Composable
private fun SkeletonBox(
    brush: Brush,
    height: Dp,
    modifier: Modifier = Modifier,
    width: Dp? = null,
    shape: Shape = RoundedCornerShape(8.dp),
) {
    Box(
        modifier = modifier
            .then(if (width != null) Modifier.size(width, height) else Modifier.height(height))
            .background(brush, shape),
    )
}

@Preview(showBackground = true)
@Composable
private fun HomescreenSkeletonPreview() {
    KidsDemoTheme {
        HomescreenSkeleton()
    }
}
