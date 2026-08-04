package com.wanx.reader.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

/* ═══════════════════════════════════════════
 * Anx 亮色 Material3 ColorScheme
 * ═══════════════════════════════════════════ */
private val AnxLightColorScheme = lightColorScheme(
    primary = AnxPrimary,
    onPrimary = AnxOnPrimary,
    primaryContainer = AnxPrimaryContainer,
    background = AnxBackground,
    onBackground = AnxOnBackground,
    surface = AnxSurface,
    onSurface = AnxOnSurface,
    surfaceVariant = Color(0xFFF2F2F7),
    onSurfaceVariant = AnxSubtext,
    outline = AnxDivider,
    outlineVariant = AnxDivider,
)

/* ═══════════════════════════════════════════
 * Anx 暗色 Material3 ColorScheme
 * ═══════════════════════════════════════════ */
private val AnxDarkColorScheme = darkColorScheme(
    primary = AnxDarkPrimary,
    onPrimary = AnxDarkOnPrimary,
    primaryContainer = AnxDarkPrimaryContainer,
    background = AnxDarkBackground,
    onBackground = AnxDarkOnBackground,
    surface = AnxDarkSurface,
    onSurface = AnxDarkOnSurface,
    surfaceVariant = Color(0xFF3A3A3C),
    onSurfaceVariant = AnxDarkSubtext,
    outline = AnxDarkDivider,
    outlineVariant = AnxDarkDivider,
)

/* ═══════════════════════════════════════════
 * Anx 护眼模式 ColorScheme（暖米色基调）
 * ═══════════════════════════════════════════ */
private val AnxEyeCareColorScheme = lightColorScheme(
    primary = Color(0xFFB8860B),           /* 暗金色，柔和不过敏 */
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFFFFE8C0),
    background = AnxEyeCareBackground,      /* #F5E6CC 暖米色 */
    onBackground = AnxEyeCareOnBackground,  /* #3D3226 深棕 */
    surface = Color(0xFFFDF3E0),
    onSurface = AnxEyeCareOnBackground,
    surfaceVariant = Color(0xFFF0E4D0),
    onSurfaceVariant = Color(0xFF6B5D4F),
    outline = Color(0xFFD4C4A8),
    outlineVariant = Color(0xFFD4C4A8),
)

/**
 * Anx 风格全局主题
 *
 * 支持四种模式：
 * - System  — 自动跟随系统暗黑模式
 * - Light   — 强制亮色
 * - Dark    — 强制暗色
 * - EyeCare — 护眼暖色模式（始终亮色基调）
 *
 * @param themeMode 主题模式，默认跟随系统
 * @param content   内容
 */
@Composable
fun AnxTheme(
    themeMode: ThemeMode = ThemeMode.System,
    content: @Composable () -> Unit,
) {
    val systemDark = isSystemInDarkTheme()

    val colorScheme = when (themeMode) {
        ThemeMode.System  -> if (systemDark) AnxDarkColorScheme else AnxLightColorScheme
        ThemeMode.Light   -> AnxLightColorScheme
        ThemeMode.Dark    -> AnxDarkColorScheme
        ThemeMode.EyeCare -> AnxEyeCareColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AnxTypography,
        content = content,
    )
}