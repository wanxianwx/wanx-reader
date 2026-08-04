package com.wanx.reader.domain.model

import androidx.compose.runtime.Immutable

/**
 * 书籍领域模型（与 Room Entity 解耦，纯 Kotlin 数据类）
 * 对应 legado 的 Book entity，但仅保留 UI 需要的字段
 *
 * @Immutable 确保 Compose 编译器将其视为不可变类型，跳过无效重组。
 */
@Immutable
data class Book(
    val id: Long,
    val name: String,
    val author: String,
    val coverUrl: String?,
    val kind: String?,
    val intro: String?,
    val origin: String,
    val originName: String,
    val latestChapterTitle: String?,
    val durChapterIndex: Int,
    val totalChapterNum: Int,
    val durChapterPos: Int,
    val bookUrl: String,
    val canUpdate: Boolean,
    val groupId: Long,
    val order: Int,
    val lastCheckTime: Long,
    val lastCheckCount: Int,
    val isInShelf: Boolean,
)