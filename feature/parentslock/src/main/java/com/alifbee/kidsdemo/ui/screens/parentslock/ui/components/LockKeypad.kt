package com.alifbee.kidsdemo.ui.screens.parentslock.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alifbee.kidsdemo.ui.common.PushButton
import com.alifbee.kidsdemo.ui.theme.LocalCustomColors

@Composable
internal fun LockKeypad(
    onDigitClicked: (Int) -> Unit,
    onDeleteClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row {
            LockKey(text = "1", onClick = { onDigitClicked(1) })
            LockKey(text = "2", onClick = { onDigitClicked(2) })
            LockKey(text = "3", onClick = { onDigitClicked(3) })
        }
        Row {
            LockKey(text = "4", onClick = { onDigitClicked(4) })
            LockKey(text = "5", onClick = { onDigitClicked(5) })
            LockKey(text = "6", onClick = { onDigitClicked(6) })
        }
        Row {
            LockKey(text = "7", onClick = { onDigitClicked(7) })
            LockKey(text = "8", onClick = { onDigitClicked(8) })
            LockKey(text = "9", onClick = { onDigitClicked(9) })
        }
        Row {
            Spacer(modifier = Modifier.width(96.dp))
            LockKey(text = "0", onClick = { onDigitClicked(0) })
            LockKey(
                text = "<<",
                onClick = onDeleteClicked,
                faceColor = LocalCustomColors.current.keyBackspace,
                baseColor = LocalCustomColors.current.keyBackspaceShadow,
                textColor = MaterialTheme.colorScheme.onPrimary,
            )
        }
    }
}

@Composable
private fun LockKey(
    text: String,
    onClick: () -> Unit,
    faceColor: Color = MaterialTheme.colorScheme.surface,
    baseColor: Color = LocalCustomColors.current.keyBase,
    textColor: Color = LocalCustomColors.current.brandPurple,
) {
    PushButton(
        text = text,
        onClick = onClick,
        modifier = Modifier
            .padding(4.dp)
            .width(88.dp),
        textColor = textColor,
        faceColor = faceColor,
        baseColor = baseColor,
        cornerRadius = 24.dp,
        depth = 4.dp,
    )
}

@Preview(showBackground = true)
@Composable
private fun LockKeypadPreview() {
    LockKeypad(
        onDigitClicked = {},
        onDeleteClicked = {}
    )
}


