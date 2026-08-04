package com.wanx.reader.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

/**
 * Anx 风格顶部导航栏
 * 设计：纯色背景（或透明），居左标题，56dp 高度，无分割线
 */
@Composable
fun AnxTopAppBar(
    title: String,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.Transparent,
    actions: @Composable (() -> Unit)? = null,
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                ),
                color = MaterialTheme.colorScheme.onBackground,
            )
        },
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = backgroundColor,
            scrolledContainerColor = backgroundColor,
        ),
        actions = {
            actions?.invoke()
            /* 右侧留白 8dp，保持视觉呼吸感 */
            androidx.compose.foundation.layout.Spacer(
                modifier = Modifier.padding(end = 8.dp)
            )
        },
    )
}