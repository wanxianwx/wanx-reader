package com.wanx.reader.ui.theme

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

/**
 * 全局主题 ViewModel
 *
 * 管理主题模式切换（System / Light / Dark / EyeCare），
 * 供 Compose UI 顶层消费。
 */
@HiltViewModel
class ThemeViewModel @Inject constructor() : ViewModel() {

    private val _themeMode = MutableStateFlow(ThemeMode.System)
    val themeMode: StateFlow<ThemeMode> = _themeMode.asStateFlow()

    fun setThemeMode(mode: ThemeMode) {
        _themeMode.value = mode
    }

    /** 循环切换：System → Light → Dark → EyeCare → System */
    fun cycleThemeMode() {
        _themeMode.value = when (_themeMode.value) {
            ThemeMode.System  -> ThemeMode.Light
            ThemeMode.Light   -> ThemeMode.Dark
            ThemeMode.Dark    -> ThemeMode.EyeCare
            ThemeMode.EyeCare -> ThemeMode.System
        }
    }
}