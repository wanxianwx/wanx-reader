package com.wanx.reader.ui.bookshelf

import androidx.compose.runtime.Immutable
import com.wanx.reader.domain.model.Book

/**
 * 书架页面 UI 状态
 *
 * 单一数据源：ViewModel 持有 MutableStateFlow<BookshelfUiState>，
 * Compose UI 通过 collectAsStateWithLifecycle() 收集。
 */
@Immutable
data class BookshelfUiState(
    /** 书籍列表 */
    val books: List<BookItem> = emptyList(),
    /** 是否正在加载 */
    val isLoading: Boolean = false,
    /** 是否正在下拉刷新 */
    val isRefreshing: Boolean = false,
    /** 错误信息，null 表示无错误 */
    val error: String? = null,
    /** 是否为空书架 */
    val isEmpty: Boolean = true,
)

/**
 * 书架中展示的书籍项（UI 层专用，与领域模型解耦）
 */
@Immutable
data class BookItem(
    val id: Long,
    val bookUrl: String,
    val title: String,
    val author: String,
    val coverUrl: String,
    val kind: String?,
    /** 阅读进度 0.0 ~ 1.0 */
    val readingProgress: Float,
    /** 最新章节标题 */
    val latestChapter: String?,
    /** 是否有更新 */
    val hasUpdate: Boolean,
    /** 来源名称 */
    val originName: String,
)

/**
 * 将领域模型 Book 转换为 UI 层 BookItem
 */
fun Book.toBookItem(): BookItem = BookItem(
    id = id,
    bookUrl = bookUrl,
    title = name,
    author = author,
    coverUrl = coverUrl ?: "",
    kind = kind,
    readingProgress = if (totalChapterNum > 0) {
        (durChapterIndex.toFloat() / totalChapterNum).coerceIn(0f, 1f)
    } else 0f,
    latestChapter = latestChapterTitle,
    hasUpdate = canUpdate,
    originName = originName,
)