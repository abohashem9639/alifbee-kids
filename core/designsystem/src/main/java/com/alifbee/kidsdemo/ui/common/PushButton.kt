package com.alifbee.kidsdemo.ui.common

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alifbee.kidsdemo.ui.theme.KidsDemoTheme
import com.alifbee.kidsdemo.ui.theme.LocalCustomColors

@Composable
fun PushButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: Painter? = null,
    textColor: Color = MaterialTheme.colorScheme.onPrimary,
    faceColor: Color = MaterialTheme.colorScheme.primary,
    baseColor: Color = LocalCustomColors.current.brandPurple,
    cornerRadius: Dp = 28.dp,
    depth: Dp = 6.dp,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val pressed by interactionSource.collectIsPressedAsState()
    val faceOffset by animateDpAsState(
        targetValue = if (pressed) depth else 0.dp,
        animationSpec = tween(120),
    )
    val shape = RoundedCornerShape(cornerRadius)

    Box(
        modifier = modifier
            .width(IntrinsicSize.Max)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick,
            ),
    ) {
        Box(
            modifier = Modifier
                .matchParentSize()
                .padding(top = depth)
                .background(baseColor, shape),
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = depth)
                .offset(y = faceOffset)
                .background(
                    brush = Brush.verticalGradient(
                        listOf(lerp(faceColor, Color.White, 0.2f), faceColor)
                    ),
                    shape = shape,
                )
                .background(
                    brush = Brush.verticalGradient(
                        listOf(Color.White.copy(alpha = 0.25f), Color.Transparent)
                    ),
                    shape = shape,
                )
                .border(2.dp, baseColor, shape)
                .padding(horizontal = 20.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            icon?.let {
                Image(painter = it, contentDescription = null)
                Spacer(Modifier.width(10.dp))
            }
            Text(
                text = text,
                color = textColor,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}

@Preview
@Composable
private fun PushButtonPreview() {
    KidsDemoTheme {
        PushButton(
            text = "More By Us",
            onClick = {},
        )
    }
}
