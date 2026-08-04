package com.wanx.reader.ui.reader

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Brightness6
import androidx.compose.material.icons.filled.FormatSize
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

/**
 * 阅读器顶部控制栏 — 半透明沉浸式
 *
 * 设计：
 * - 56dp 高度
 * - 黑色半透明渐变背景（0.35 → 透明）
 * - 左侧返回按钮 + 中间书名 + 右侧更多按钮
 */
@Composable
fun ReaderTopBar(
    title: String,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color.Black.copy(alpha = 0.35f),
                        Color.Transparent,
                    ),
                ),
            ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "返回",
                    tint = Color.White,
                )
            }
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = Color.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f),
            )
            IconButton(onClick = { /* TODO: 更多菜单 */ }) {
                Icon(
                    imageVector = Icons.Default.MoreHoriz,
                    contentDescription = "更多",
                    tint = Color.White,
                )
            }
        }
    }
}

/**
 * 阅读器底部控制栏 — 进度条 + 快捷操作
 *
 * 设计：
 * - 黑色半透明渐变背景（透明 → 0.45）
 * - 阅读进度 Slider
 * - 快捷操作：目录 / 亮度 / 字体 / 朗读
 */
@Composable
fun ReaderBottomBar(
    progress: Float,
    onProgressChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color.Transparent,
                        Color.Black.copy(alpha = 0.45f),
                    ),
                ),
            )
            .padding(bottom = 8.dp),
    ) {
        /* 进度条 */
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "${(progress * 100).toInt()}%",
                style = MaterialTheme.typography.labelSmall,
                color = Color.White.copy(alpha = 0.8f),
            )
            Slider(
                value = progress,
                onValueChange = onProgressChange,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp),
                colors = SliderDefaults.colors(
                    thumbColor = Color.White,
                    activeTrackColor = Color.White.copy(alpha = 0.9f),
                    inactiveTrackColor = Color.White.copy(alpha = 0.3f),
                ),
            )
            Text(
                text = "100%",
                style = MaterialTheme.typography.labelSmall,
                color = Color.White.copy(alpha = 0.8f),
            )
        }

        /* 快捷操作栏 */
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ReaderToolButton(
                icon = Icons.Default.MenuBook,
                label = "目录",
                onClick = { /* TODO */ },
            )
            ReaderToolButton(
                icon = Icons.Default.Brightness6,
                label = "亮度",
                onClick = { /* TODO */ },
            )
            ReaderToolButton(
                icon = Icons.Default.FormatSize,
                label = "字体",
                onClick = { /* TODO */ },
            )
            ReaderToolButton(
                icon = Icons.Default.PlayArrow,
                label = "朗读",
                onClick = { /* TODO */ },
            )
        }
    }
}

/**
 * 控制栏快捷按钮
 */
@Composable
internal fun ReaderToolButton(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .clickable(onClick = onClick)
            .padding(8.dp),
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = Color.White.copy(alpha = 0.9f),
            modifier = Modifier.size(24.dp),
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = Color.White.copy(alpha = 0.8f),
        )
    }
}