package com.wanx.reader.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

data class AnxBottomNavItem(
    val label: String,
    val icon: ImageVector,
    val route: String,
)

/**
 * Anx 风格底部导航栏
 * 参考 legado-with-MD3 的 AppNavigationBar 设计
 * - 毛玻璃/半透明背景
 * - 选中态用主色高亮胶囊
 */
@Composable
fun AnxBottomBar(
    items: List<AnxBottomNavItem>,
    currentRoute: String,
    onItemClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    /* 半透明毛玻璃背景 */
    val surfaceColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.95f)

    NavigationBar(
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp),
        containerColor = surfaceColor,
        tonalElevation = 0.dp,
    ) {
        items.forEach { item ->
            val selected = currentRoute == item.route
            AnxBottomBarItem(
                selected = selected,
                onClick = { onItemClick(item.route) },
                icon = item.icon,
                label = item.label,
                selectedColor = MaterialTheme.colorScheme.primary,
            )
        }
    }
}

@Composable
private fun RowScope.AnxBottomBarItem(
    selected: Boolean,
    onClick: () -> Unit,
    icon: ImageVector,
    label: String,
    selectedColor: Color,
) {
    /* 选中态：胶囊背景 + 图标居中 */
    if (selected) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = ripple(bounded = false, radius = 32.dp),
                    onClick = onClick,
                ),
            contentAlignment = Alignment.Center,
        ) {
            Box(
                modifier = Modifier
                    .width(64.dp)
                    .height(32.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(selectedColor.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center,
            ) {
                androidx.compose.material3.Icon(
                    imageVector = icon,
                    contentDescription = label,
                    modifier = Modifier.size(24.dp),
                    tint = selectedColor,
                )
            }
        }
    } else {
        NavigationBarItem(
            selected = false,
            onClick = onClick,
            icon = {
                androidx.compose.material3.Icon(
                    imageVector = icon,
                    contentDescription = label,
                    modifier = Modifier.size(24.dp),
                )
            },
            label = {
                Text(
                    text = label,
                    style = MaterialTheme.typography.labelSmall,
                )
            },
            colors = NavigationBarItemDefaults.colors(
                unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
            ),
        )
    }
}