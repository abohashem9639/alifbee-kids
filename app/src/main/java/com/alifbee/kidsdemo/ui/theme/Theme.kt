package com.alifbee.kidsdemo.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf

private val lightScheme = lightColorScheme(
    primary = primaryLight,
    onPrimary = onPrimaryLight,
    primaryContainer = primaryContainerLight,
    onPrimaryContainer = onPrimaryContainerLight,
    secondary = secondaryLight,
    onSecondary = onSecondaryLight,
    secondaryContainer = secondaryContainerLight,
    onSecondaryContainer = onSecondaryContainerLight,
    tertiary = tertiaryLight,
    onTertiary = onTertiaryLight,
    tertiaryContainer = tertiaryContainerLight,
    onTertiaryContainer = onTertiaryContainerLight,
    error = errorLight,
    onError = onErrorLight,
    background = backgroundLight,
    onBackground = onBackgroundLight,
    surface = surfaceLight,
    onSurface = onSurfaceLight,
    surfaceVariant = surfaceVariantLight,
    onSurfaceVariant = onSurfaceVariantLight,
    outline = outlineLight,
)

private val darkScheme = darkColorScheme(
    primary = primaryDark,
    onPrimary = onPrimaryDark,
    primaryContainer = primaryContainerDark,
    onPrimaryContainer = onPrimaryContainerDark,
    secondary = secondaryDark,
    onSecondary = onSecondaryDark,
    secondaryContainer = secondaryContainerDark,
    onSecondaryContainer = onSecondaryContainerDark,
    tertiary = tertiaryDark,
    onTertiary = onTertiaryDark,
    tertiaryContainer = tertiaryContainerDark,
    onTertiaryContainer = onTertiaryContainerDark,
    error = errorDark,
    onError = onErrorDark,
    background = backgroundDark,
    onBackground = onBackgroundDark,
    surface = surfaceDark,
    onSurface = onSurfaceDark,
    surfaceVariant = surfaceVariantDark,
    onSurfaceVariant = onSurfaceVariantDark,
    outline = outlineDark,
)

val LocalCustomColors = staticCompositionLocalOf { CustomColorsPalette() }

@Composable
fun KidsDemoTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colorScheme = if (darkTheme) darkScheme else lightScheme
    val customColorsPalette = CustomColorsPalette(
        brandYellow = brandYellow,
        brandPurple = brandPurple,
        plumPurple = plumPurple,
        moreByUsBase = moreByUsBase,
        keyBase = keyBase,
        keyBackspace = keyBackspace,
        keyBackspaceShadow = keyBackspaceShadow,
        cardBorder = cardBorder,
        linkBlue = linkBlue,
        profileStatsBg = profileStatsBg,
        settingsButtonBg = settingsButtonBg,
        courseCardBg = courseCardBg,
        courseProgressTrack = courseProgressTrack,
        featureCircleBg = featureCircleBg,
        statisticCardBg = statisticCardBg,
        dayGlow = dayGlow,
        dayCurrentBg = dayCurrentBg,
        dayEmptyStroke = dayEmptyStroke,
        dayMissedGlow = dayMissedGlow,
        dayMissedBg = dayMissedBg,
        assignmentTrack = assignmentTrack,
        statusTodo = statusTodo,
        statusDone = statusDone,
        pricingCardGray = pricingCardGray,
        pricingCardLavender = pricingCardLavender,
        pricingBadgeBg = pricingBadgeBg,
        skeletonPlaceholder = skeletonPlaceholder,
        skeletonHighlight = skeletonHighlight,
        reviewItemIconBg = reviewItemIconBg,
        reviewItemDivider = reviewItemDivider,
    )
    CompositionLocalProvider(LocalCustomColors provides customColorsPalette) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content,
        )
    }
}
