package com.wanx.reader.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wanx.reader.data.local.UpdatePreferences
import com.wanx.reader.data.remote.UpdateChecker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

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
                    updatePreferences.saveCommitSha(remoteSha)
                } else {
                    val savedSha = updatePreferences.lastCommitSha.firstOrNull()
                    if (savedSha != null && savedSha != remoteSha) {
                        _releaseUrl.value = updateChecker.getReleaseUrl()
                        _showUpdateDialog.value = true
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