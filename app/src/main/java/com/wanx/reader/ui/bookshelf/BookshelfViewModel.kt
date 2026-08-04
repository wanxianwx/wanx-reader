package com.wanx.reader.ui.bookshelf

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wanx.reader.data.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 书架页面 ViewModel
 *
 * 设计原则：
 * - 使用 StateFlow 暴露 UI 状态（非 LiveData）
 * - 使用 SharedFlow 暴露一次性事件（如 SnackBar、导航）
 * - 不持有 Context、Activity、View 引用
 * - 通过 @HiltViewModel + @Inject 注入 BookRepository
 */
@HiltViewModel
class BookshelfViewModel @Inject constructor(
    private val bookRepository: BookRepository,
) : ViewModel() {

    /* ── UI 状态 ── */

    private val _isRefreshing = MutableStateFlow(false)
    private val _error = MutableStateFlow<String?>(null)

    /**
     * 书架 UI 状态
     * 组合 books Flow + 刷新状态 + 错误状态
     */
    val uiState: StateFlow<BookshelfUiState> = combine(
        bookRepository.observeBookshelf(),
        _isRefreshing,
        _error,
    ) { books, isRefreshing, error ->
        BookshelfUiState(
            books = books.map { it.toBookItem() },
            isLoading = false,
            isRefreshing = isRefreshing,
            error = error,
            isEmpty = books.isEmpty(),
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = BookshelfUiState(isLoading = true),
    )

    /* ── 一次性事件（SnackBar、Toast 等）── */

    private val _events = MutableSharedFlow<BookshelfEvent>()
    val events = _events.asSharedFlow()

    /* ── 用户操作 ── */

    /** 下拉刷新 */
    fun refresh() {
        viewModelScope.launch {
            _isRefreshing.value = true
            _error.value = null
            bookRepository.refreshBookshelf()
                .onSuccess {
                    _events.emit(BookshelfEvent.RefreshSuccess)
                }
                .onFailure { e ->
                    _error.value = e.message ?: "刷新失败"
                    _events.emit(BookshelfEvent.RefreshFailed(e.message ?: "未知错误"))
                }
            _isRefreshing.value = false
        }
    }

    /** 清除错误 */
    fun clearError() {
        _error.value = null
    }

    /** 删除书籍 */
    fun removeBook(bookUrl: String) {
        viewModelScope.launch {
            bookRepository.removeBook(bookUrl)
            _events.emit(BookshelfEvent.BookRemoved)
        }
    }
}

/**
 * 书架一次性事件
 */
sealed interface BookshelfEvent {
    data object RefreshSuccess : BookshelfEvent
    data class RefreshFailed(val message: String) : BookshelfEvent
    data object BookRemoved : BookshelfEvent
}