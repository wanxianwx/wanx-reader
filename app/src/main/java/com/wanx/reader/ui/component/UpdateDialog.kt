package com.wanx.reader.ui.component

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

/**
 * 更新提示对话框
 *
 * 设计：
 * - 仅两个按钮：「去下载」和「忽略」
 * - 不包含任何自动下载 APK 的代码
 * - 点击「去下载」跳转浏览器打开 Release 页面
 *
 * @param releaseUrl   Release 页面地址
 * @param onDismiss    忽略按钮回调
 * @param onGoDownload 去下载按钮回调
 */
@Composable
fun UpdateDialog(
    releaseUrl: String,
    onDismiss: () -> Unit,
    onGoDownload: (String) -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "发现新版本",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                ),
            )
        },
        text = {
            Text(
                text = "检测到 Git 仓库有新的代码提交（commit）。\n\n" +
                    "点击「去下载」将跳转浏览器打开 Release 页面，\n" +
                    "请手动下载安装最新版本。",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Start,
            )
        },
        confirmButton = {
            TextButton(
                onClick = { onGoDownload(releaseUrl) },
            ) {
                Text(
                    text = "去下载",
                    color = MaterialTheme.colorScheme.primary,
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    text = "忽略",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        },
    )
}