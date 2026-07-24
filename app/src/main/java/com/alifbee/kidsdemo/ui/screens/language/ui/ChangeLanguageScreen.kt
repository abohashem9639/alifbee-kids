package com.alifbee.kidsdemo.ui.screens.language.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alifbee.kidsdemo.R
import com.alifbee.kidsdemo.data.model.AppLanguage
import com.alifbee.kidsdemo.ui.common.CollectEffect
import com.alifbee.kidsdemo.ui.screens.language.ui.components.LanguageRow
import com.alifbee.kidsdemo.ui.screens.language.ui.viewmodel.ChangeLanguageContract
import com.alifbee.kidsdemo.ui.theme.KidsDemoTheme
import com.alifbee.kidsdemo.ui.theme.LocalCustomColors
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Composable
internal fun ChangeLanguageScreen(
    state: ChangeLanguageContract.State,
    effectFlow: Flow<ChangeLanguageContract.Effect>,
    onEventSent: (event: ChangeLanguageContract.Event) -> Unit,
    onCloseClicked: () -> Unit,
    onDone: () -> Unit,
) {
    effectFlow.CollectEffect { effect ->
        when (effect) {
            ChangeLanguageContract.Effect.NavigateBack -> onDone()
        }
    }

    ChangeLanguageContent(
        state = state,
        onEventSent = onEventSent,
        onCloseClicked = onCloseClicked,
    )
}

@Composable
private fun ChangeLanguageContent(
    state: ChangeLanguageContract.State,
    onEventSent: (event: ChangeLanguageContract.Event) -> Unit,
    onCloseClicked: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .systemBarsPadding()
            .padding(horizontal = 16.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
        ) {
            Text(
                text = stringResource(R.string.change_language_title),
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f),
            )
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(LocalCustomColors.current.settingsButtonBg)
                    .clickable(onClick = onCloseClicked),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_close),
                    contentDescription = stringResource(R.string.cd_close),
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.size(24.dp),
                )
            }
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .weight(1f)
                .padding(top = 24.dp),
        ) {
            state.languages.forEach { language ->
                LanguageRow(
                    language = language,
                    isSelected = language.code == state.selectedCode,
                    onClick = { onEventSent(ChangeLanguageContract.Event.OnLanguageSelected(language.code)) },
                )
            }
        }

        val hasSelection = state.selectedCode.isNotBlank()
        val buttonColor =
            if (hasSelection) LocalCustomColors.current.brandYellow else LocalCustomColors.current.settingsButtonBg
        Button(
            onClick = { onEventSent(ChangeLanguageContract.Event.OnDoneClicked) },
            enabled = !state.isUpdating,
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = buttonColor,
                contentColor = MaterialTheme.colorScheme.onBackground,
                disabledContainerColor = buttonColor,
                disabledContentColor = MaterialTheme.colorScheme.onBackground,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
                .height(52.dp),
        ) {
            if (state.isUpdating) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.onBackground,
                    strokeWidth = 2.dp,
                    modifier = Modifier.size(24.dp),
                )
            } else {
                Text(
                    text = stringResource(R.string.done),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ChangeLanguageScreenPreview() {
    KidsDemoTheme {
        ChangeLanguageScreen(
            state = ChangeLanguageContract.State(
                languages = listOf(
                    AppLanguage(code = "en", name = "English", flagRes = R.drawable.img_us_flag),
                    AppLanguage(code = "tr", name = "Türkçe", flagRes = R.drawable.ic_tr_flag),
                    AppLanguage(code = "ar", name = "العربية", flagRes = R.drawable.ic_ar_flag),
                ),
                selectedCode = "en",
            ),
            effectFlow = emptyFlow(),
            onEventSent = {},
            onCloseClicked = {},
            onDone = {},
        )
    }
}
