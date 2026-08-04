package com.wanx.reader.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

/**
 * Anx 风格呼吸感渐变背景
 * 静谧柔和，无动画
 */
@Composable
fun AnxGradientBackground(content: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color(0xFFE8F0FE),
                        Color(0xFFF3E7F9),
                        Color(0xFFFDE8E8),
                        Color(0xFFFCFCFC),
                    ),
                    center = Offset(0.2f, 0.1f),
                    radius = 2.5f,
                ),
            ),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFE8F0FE).copy(alpha = 0.15f),
                            Color(0xFFFCFCFC).copy(alpha = 0.0f),
                            Color(0xFFF3E7F9).copy(alpha = 0.10f),
                        ),
                    ),
                )
        ) {
            content()
        }
    }
}