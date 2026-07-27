package com.alifbee.kidsdemo.ui.screens.homescreen.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alifbee.kidsdemo.core.designsystem.R
import com.alifbee.kidsdemo.ui.theme.KidsDemoTheme
import com.alifbee.kidsdemo.ui.theme.LocalCustomColors

@Composable
internal fun HomescreenTopBar(
    onSettingsClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
    ) {
        Image(
            painter = painterResource(R.drawable.ic_alifbee_3),
            contentDescription = stringResource(R.string.cd_logo),
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .size(width = 74.59.dp, height = 15.6.dp),
        )
        Box(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .size(40.dp)
                .clip(CircleShape)
                .background(LocalCustomColors.current.settingsButtonBg)
                .clickable(onClick = onSettingsClicked)
                .padding(8.dp),
        ) {
            Image(
                painter = painterResource(R.drawable.ic_settings),
                contentDescription = stringResource(R.string.cd_settings),
                contentScale = ContentScale.Fit,
                modifier = Modifier.align(Alignment.Center),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomescreenTopBarPreview() {
    KidsDemoTheme {
        HomescreenTopBar(onSettingsClicked = {})
    }
}
