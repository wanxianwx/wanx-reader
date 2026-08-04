package com.wanx.reader.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

/**
 * Anx 风格动态呼吸感渐变背景
 *
 * 设计：
 * - 核心区域接近纯白，避免死板
 * - 顶部浅雾霾蓝
 * - 左下/中间带极其微弱的浅紫、浅肉粉光晕
 * - 静谧、柔和、无动画，避免打扰阅读
 */
@Composable
fun AnxGradientBackground(content: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color(0xFFE8F0FE), /* 浅雾霾蓝 */
                        Color(0xFFF3E7F9), /* 浅紫色 */
                        Color(0xFFFDE8E8), /* 浅肉粉色 */
                        Color(0xFFFCFCFC), /* 核心近白 */
                    ),
                    center = 0.2f to 0.1f, /* 光晕偏左上 */
                    radius = 2.5f,        /* 大半径，过渡柔和 */
                ),
            ),
    ) {
        /* 叠加一层极淡的垂直渐变，让整体更自然 */
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFE8F0FE).copy(alpha = 0.15f), /* 顶部更淡的蓝 */
                            Color(0xFFFCFCFC).copy(alpha = 0.0f),  /* 中部透明 */
                            Color(0xFFF3E7F9).copy(alpha = 0.10f), /* 底部极淡紫 */
                        ),
                    ),
                )
        ) {
            content()
        }
    }
}