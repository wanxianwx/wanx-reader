package com.wanx.reader.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

/* 避免重复创建 DataStore 实例 */
private val Context.updateDataStore: DataStore<Preferences> by preferencesDataStore(name = "update")

/**
 * 更新检查偏好存储
 *
 * 使用 DataStore 持久化最后一次记录的 commit SHA，
 * 每次启动时对比远程 SHA 以判断是否有新版本。
 */
@Singleton
class UpdatePreferences @Inject constructor(
    @ApplicationContext private val context: Context,
) {

    private object Keys {
        val LAST_COMMIT_SHA = stringPreferencesKey("last_commit_sha")
        val FIRST_LAUNCH_DONE = stringPreferencesKey("first_launch_done")
    }

    /** 读取上次记录的 commit SHA */
    val lastCommitSha: Flow<String?> = context.updateDataStore.data.map { prefs ->
        prefs[Keys.LAST_COMMIT_SHA]
    }

    /** 保存新的 commit SHA */
    suspend fun saveCommitSha(sha: String) {
        context.updateDataStore.edit { prefs ->
            prefs[Keys.LAST_COMMIT_SHA] = sha
        }
    }

    /** 是否已完成首次启动记录 */
    suspend fun isFirstLaunch(): Boolean {
        var first = true
        context.updateDataStore.data.collect { prefs ->
            first = prefs[Keys.FIRST_LAUNCH_DONE] == null
            return@collect /* 只取一次 */
        }
        /* 标记首次启动完成 */
        context.updateDataStore.edit { prefs ->
            prefs[Keys.FIRST_LAUNCH_DONE] = "true"
        }
        return first
    }
}