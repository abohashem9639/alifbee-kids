package com.alifbee.kidsdemo.ui

import android.content.Context
import android.content.ContextWrapper
import android.content.res.Configuration
import android.content.res.Resources
import android.text.TextUtils
import android.view.View
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import java.util.Locale

@Composable
fun ProvideAppLocale(
    languageCode: String,
    darkTheme: Boolean,
    content: @Composable () -> Unit,
) {
    val context = LocalContext.current
    val baseConfiguration = LocalConfiguration.current

    val localizedContext = remember(languageCode, darkTheme, context, baseConfiguration) {
        LocalizedContextWrapper(context, languageCode, darkTheme)
    }

    val layoutDirection = remember(languageCode) {
        if (TextUtils.getLayoutDirectionFromLocale(Locale(languageCode)) == View.LAYOUT_DIRECTION_RTL) {
            LayoutDirection.Rtl
        } else {
            LayoutDirection.Ltr
        }
    }

    CompositionLocalProvider(
        LocalContext provides localizedContext,
        LocalConfiguration provides localizedContext.resources.configuration,
        LocalLayoutDirection provides layoutDirection,
    ) {
        content()
    }
}

private class LocalizedContextWrapper(
    base: Context,
    languageCode: String,
    darkTheme: Boolean,
) : ContextWrapper(base) {

    private val localizedResources: Resources

    init {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = Configuration(base.resources.configuration).apply {
            setLocale(locale)
            setLayoutDirection(locale)
            uiMode = (uiMode and Configuration.UI_MODE_NIGHT_MASK.inv()) or
                if (darkTheme) Configuration.UI_MODE_NIGHT_YES else Configuration.UI_MODE_NIGHT_NO
        }
        localizedResources = base.createConfigurationContext(config).resources
    }

    override fun getResources(): Resources = localizedResources
}
