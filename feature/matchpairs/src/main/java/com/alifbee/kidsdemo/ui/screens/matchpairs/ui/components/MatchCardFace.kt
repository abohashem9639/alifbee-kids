package com.alifbee.kidsdemo.ui.screens.matchpairs.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alifbee.kidsdemo.ui.theme.KidsDemoTheme
import com.alifbee.kidsdemo.ui.theme.LocalCustomColors

@Composable
internal fun MatchCardFace(
    text: String,
    isDragging: Boolean,
    correct: Boolean?,
    modifier: Modifier = Modifier,
) {
    val shape = RoundedCornerShape(12.dp)
    val borderColor = when {
        correct == true -> LocalCustomColors.current.statusDone
        correct == false -> MaterialTheme.colorScheme.error
        else -> LocalCustomColors.current.statusTodo
    }
    val background =
        if (isDragging) LocalCustomColors.current.statusTodo else MaterialTheme.colorScheme.surface
    Box(
        modifier = modifier
            .fillMaxSize()
            .shadow(if (isDragging) 8.dp else 1.dp, shape)
            .background(background, shape)
            .border(2.dp, borderColor, shape)
            .padding(horizontal = 12.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MatchCardFacePreview() {
    KidsDemoTheme {
        Box(modifier = Modifier.height(56.dp)) {
            MatchCardFace(text = "The Guide", isDragging = false, correct = null)
        }
    }
}
