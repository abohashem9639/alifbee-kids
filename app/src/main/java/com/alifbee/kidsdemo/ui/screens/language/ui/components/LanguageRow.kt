package com.alifbee.kidsdemo.ui.screens.language.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alifbee.kidsdemo.R
import com.alifbee.kidsdemo.data.model.AppLanguage
import com.alifbee.kidsdemo.ui.theme.KidsDemoTheme
import com.alifbee.kidsdemo.ui.theme.LocalCustomColors

@Composable
internal fun LanguageRow(
    language: AppLanguage,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(
                if (isSelected) LocalCustomColors.current.courseCardBg else MaterialTheme.colorScheme.surfaceVariant
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 16.dp),
    ) {
        Image(
            painter = painterResource(language.flagRes),
            contentDescription = stringResource(R.string.cd_language_flag),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(width = 32.dp, height = 24.dp)
                .clip(RoundedCornerShape(4.dp)),
        )
        Text(
            text = language.name,
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 16.dp),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun LanguageRowPreview() {
    KidsDemoTheme {
        LanguageRow(
            language = AppLanguage(code = "en", name = "English", flagRes = R.drawable.img_us_flag),
            isSelected = true,
            onClick = {},
        )
    }
}
