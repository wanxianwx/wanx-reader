package com.wanx.reader.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Anx 风格圆角卡片
 * 设计：16dp 圆角，2dp 轻微阴影，高留白内边距
 *
 * @param modifier 外部修饰符
 * @param elevation 阴影高度，默认 2dp
 * @param cornerRadius 圆角半径，默认 16dp
 * @param contentPadding 内边距，默认 16dp
 * @param content 卡片内容
 */
@Composable
fun AnxRoundedCard(
    modifier: Modifier = Modifier,
    elevation: Dp = 2.dp,
    cornerRadius: Dp = 16.dp,
    contentPadding: Dp = 16.dp,
    content: @Composable () -> Unit,
) {
    Box(
        modifier = modifier
            .shadow(
                elevation = elevation,
                shape = RoundedCornerShape(cornerRadius),
                /* 使用 surfaceColorAtElevation 模拟 Material 阴影 */
                ambientColor = MaterialTheme.colorScheme.outline,
                spotColor = MaterialTheme.colorScheme.outline,
            )
            .clip(RoundedCornerShape(cornerRadius))
            .background(MaterialTheme.colorScheme.surfaceColorAtElevation(elevation))
            .padding(contentPadding),
    ) {
        content()
    }
}