package com.wanx.reader.ui.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.Brightness6
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.CloudSync
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Source
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wanx.reader.ui.component.AnxRoundedCard
import com.wanx.reader.ui.theme.ThemeMode
import com.wanx.reader.ui.theme.ThemeViewModel

/**
 * 我的 / 设置页面 — Anx 风格
 *
 * 设计（参考 anx-reader）：
 * - 顶部大标题 "Wanx"
 * - 设置项分组，每组用 AnxRoundedCard 包裹
 * - 高留白：组间距 24dp，菜单项内边距 20dp
 * - 每项：左图标 + 标题 + 副标题 + 右箭头
 */
@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    themeViewModel: ThemeViewModel = hiltViewModel(),
) {
    val themeMode by themeViewModel.themeMode.collectAsStateWithLifecycle()
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
    ) {
        /* 顶部标题 */
        item {
            Spacer(modifier = Modifier.height(60.dp))
            Text(
                text = "Wanx",
                style = MaterialTheme.typography.displayLarge.copy(
                    fontWeight = FontWeight.Bold,
                ),
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 20.dp),
            )
        }

        /* 第一组：阅读 */
        item {
            SectionTitle("阅读")
            AnxRoundedCard(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = 4.dp,
            ) {
                Column {
                    MenuRow(
                        icon = Icons.Default.AutoStories,
                        title = "阅读记录",
                        subtitle = "查看阅读时长与统计",
                    )
                    MenuDivider()
                    MenuRow(
                        icon = Icons.Default.Brightness6,
                        title = "主题模式",
                        subtitle = themeModeLabel(themeMode),
                        onClick = { themeViewModel.cycleThemeMode() },
                    )
                    MenuDivider()
                    MenuRow(
                        icon = Icons.Default.Palette,
                        title = "阅读主题",
                        subtitle = "自定义背景、字体、颜色",
                    )
                }
            }
        }

        /* 第二组：数据 */
        item {
            Spacer(modifier = Modifier.height(24.dp))
            SectionTitle("数据")
            AnxRoundedCard(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = 4.dp,
            ) {
                Column {
                    MenuRow(
                        icon = Icons.Default.Source,
                        title = "书源管理",
                        subtitle = "添加、编辑、导入书源",
                    )
                    MenuDivider()
                    MenuRow(
                        icon = Icons.Default.CloudSync,
                        title = "备份与同步",
                        subtitle = "WebDAV 云同步",
                    )
                    MenuDivider()
                    MenuRow(
                        icon = Icons.Default.Language,
                        title = "语言",
                        subtitle = "切换应用语言",
                    )
                }
            }
        }

        /* 第三组：其他 */
        item {
            Spacer(modifier = Modifier.height(24.dp))
            SectionTitle("其他")
            AnxRoundedCard(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = 4.dp,
            ) {
                Column {
                    MenuRow(
                        icon = Icons.Default.Info,
                        title = "关于 Wanx Reader",
                        subtitle = "版本 1.0.0",
                    )
                    MenuDivider()
                    MenuRow(
                        icon = Icons.AutoMirrored.Filled.ExitToApp,
                        title = "退出",
                        subtitle = null,
                        titleColor = MaterialTheme.colorScheme.error,
                    )
                }
            }
        }

        /* 底部留白 */
        item {
            Spacer(modifier = Modifier.height(88.dp))
        }
    }
}

/* ── 内部组件 ── */

@Composable
private fun SectionTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleSmall.copy(
            fontWeight = FontWeight.SemiBold,
        ),
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = Modifier.padding(start = 4.dp, bottom = 8.dp),
    )
}

@Composable
private fun MenuRow(
    icon: ImageVector,
    title: String,
    subtitle: String?,
    titleColor: Color = MaterialTheme.colorScheme.onSurface,
    onClick: () -> Unit = {},
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = if (titleColor == MaterialTheme.colorScheme.error) {
                titleColor
            } else {
                MaterialTheme.colorScheme.onSurfaceVariant
            },
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                color = titleColor,
            )
            if (subtitle != null) {
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = 2.dp),
                )
            }
        }
        Icon(
            imageVector = Icons.Default.ChevronRight,
            contentDescription = null,
            modifier = Modifier.size(20.dp),
            tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
        )
    }
}

@Composable
private fun MenuDivider() {
    HorizontalDivider(
        modifier = Modifier.padding(start = 60.dp),
        thickness = 0.5.dp,
        color = MaterialTheme.colorScheme.outlineVariant,
    )
}

/** 主题模式中文标签 */
private fun themeModeLabel(mode: ThemeMode): String = when (mode) {
    ThemeMode.System  -> "跟随系统"
    ThemeMode.Light   -> "亮色模式"
    ThemeMode.Dark    -> "暗色模式"
    ThemeMode.EyeCare -> "护眼模式"
}