package com.wanx.reader.ui.reader

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.viewinterop.AndroidView

/**
 * 阅读器页面
 *
 * 设计：
 * - 核心阅读层：用 AndroidView 包装原 WebView/Canvas（当前为占位 View）
 * - 控制栏：Compose 半透明沉浸式效果（见 ReaderControlBar.kt）
 * - 点击中间区域切换控制栏可见性
 *
 * @param bookTitle 书名
 * @param bookUrl 书籍 URL
 * @param onBack 返回回调
 */
@Composable
fun ReaderScreen(
    bookTitle: String = "三体",
    bookUrl: String = "book://1",
    onBack: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    var showControls by remember { mutableStateOf(true) }
    var readingProgress by remember { mutableStateOf(0.35f) }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = Color.Transparent,
        topBar = {
            if (showControls) {
                ReaderTopBar(
                    title = bookTitle,
                    onBack = onBack,
                )
            }
        },
        bottomBar = {
            if (showControls) {
                ReaderBottomBar(
                    progress = readingProgress,
                    onProgressChange = { readingProgress = it },
                )
            }
        },
    ) { innerPadding ->
        /* 核心阅读区域 — AndroidView 包装 */
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .clickable { showControls = !showControls },
        ) {
            AndroidView(
                modifier = Modifier.fillMaxSize(),
                factory = { context ->
                    /* TODO: 替换为 legado 的 ReadView (WebView/Canvas) */
                    android.widget.TextView(context).apply {
                        textSize = 18f
                        setLineSpacing(8f, 1.2f)
                        setPadding(48, 48, 48, 48)
                        setTextColor(0xFF1C1C1E.toInt())
                        setBackgroundColor(0xFFF5E6CC.toInt()) /* 护眼米色 */
                        text = "《$bookTitle》\n\n" +
                            "（阅读器核心区域）\n\n" +
                            "此处将嵌入 legado 的 ReadView（WebView / Canvas 分页引擎）。\n\n" +
                            "当前为占位视图。\n\n" +
                            "点击屏幕中间区域可切换控制栏的显示/隐藏。\n\n" +
                            "顶部控制栏为半透明渐变效果，底部控制栏包含阅读进度条和快捷操作。"
                    }
                },
            )
        }
    }
}