package com.wanx.reader.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val AnxLightColorScheme = lightColorScheme(
    primary = AnxPrimary,
    onPrimary = AnxOnPrimary,
    background = AnxBackground,
    onBackground = AnxOnBackground,
    surface = AnxSurface,
    onSurface = AnxOnSurface,
    surfaceVariant = Color(0xFFF2F2F7),
    onSurfaceVariant = AnxSubtext,
    outline = AnxDivider,
    outlineVariant = AnxDivider,
)

private val AnxDarkColorScheme = darkColorScheme(
    primary = AnxDarkPrimary,
    onPrimary = AnxDarkOnPrimary,
    background = AnxDarkBackground,
    onBackground = AnxDarkOnBackground,
    surface = AnxDarkSurface,
    onSurface = AnxDarkOnSurface,
    surfaceVariant = Color(0xFF3A3A3C),
    onSurfaceVariant = AnxDarkSubtext,
    outline = AnxDarkDivider,
    outlineVariant = AnxDarkDivider,
)

private val AnxEyeCareColorScheme = lightColorScheme(
    primary = Color(0xFFB8860B),
    onPrimary = Color(0xFFFFFFFF),
    background = AnxEyeCareBackground,
    onBackground = AnxEyeCareOnBackground,
    surface = Color(0xFFFDF3E0),
    onSurface = AnxEyeCareOnBackground,
    surfaceVariant = Color(0xFFF0E4D0),
    onSurfaceVariant = Color(0xFF6B5D4F),
    outline = Color(0xFFD4C4A8),
    outlineVariant = Color(0xFFD4C4A8),
)

@Composable
fun AnxTheme(
    themeMode: ThemeMode = ThemeMode.System,
    content: @Composable () -> Unit,
) {
    val systemDark = isSystemInDarkTheme()

    val colorScheme = when (themeMode) {
        ThemeMode.System -> if (systemDark) AnxDarkColorScheme else AnxLightColorScheme
        ThemeMode.Light -> AnxLightColorScheme
        ThemeMode.Dark -> AnxDarkColorScheme
        ThemeMode.EyeCare -> AnxEyeCareColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AnxTypography,
        content = content,
    )
}