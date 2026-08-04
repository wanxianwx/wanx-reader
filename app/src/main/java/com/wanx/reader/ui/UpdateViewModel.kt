package com.wanx.reader.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wanx.reader.data.local.UpdatePreferences
import com.wanx.reader.data.remote.UpdateChecker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 更新检查 ViewModel
 *
 * 启动时自动检查指定 Git 仓库的最新 commit SHA，
 * 与本地 DataStore 记录的 SHA 对比，判断是否有新版本。
 */
@HiltViewModel
class UpdateViewModel @Inject constructor(
    private val updateChecker: UpdateChecker,
    private val updatePreferences: UpdatePreferences,
) : ViewModel() {

    private val _showUpdateDialog = MutableStateFlow(false)
    val showUpdateDialog: StateFlow<Boolean> = _showUpdateDialog.asStateFlow()

    private val _releaseUrl = MutableStateFlow("")
    val releaseUrl: StateFlow<String> = _releaseUrl.asStateFlow()

    init {
        checkForUpdate()
    }

    private fun checkForUpdate() {
        viewModelScope.launch {
            try {
                val isFirst = updatePreferences.isFirstLaunch()
                val remoteSha = updateChecker.fetchLatestCommitSha() ?: return@launch

                if (isFirst) {
                    /* 首次启动：记录当前 SHA，不弹窗 */
                    updatePreferences.saveCommitSha(remoteSha)
                } else {
                    /* 非首次启动：对比 SHA */
                    val localSha: String? = null
                    updatePreferences.lastCommitSha.collect { sha ->
                        val saved = sha
                        if (saved != null && saved != remoteSha) {
                            _releaseUrl.value = updateChecker.getReleaseUrl()
                            _showUpdateDialog.value = true
                        }
                        return@collect
                    }
                }
            } catch (_: Exception) {
                /* 网络异常静默处理 */
            }
        }
    }

    fun dismissUpdateDialog() {
        _showUpdateDialog.value = false
    }
}