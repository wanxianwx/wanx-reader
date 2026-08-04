package com.wanx.reader.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.updateDataStore: DataStore<Preferences> by preferencesDataStore(name = "update")

@Singleton
class UpdatePreferences @Inject constructor(
    @ApplicationContext private val context: Context,
) {
    private object Keys {
        val LAST_COMMIT_SHA = stringPreferencesKey("last_commit_sha")
        val FIRST_LAUNCH_DONE = stringPreferencesKey("first_launch_done")
    }

    val lastCommitSha = context.updateDataStore.data.map { prefs ->
        prefs[Keys.LAST_COMMIT_SHA]
    }

    suspend fun saveCommitSha(sha: String) {
        context.updateDataStore.edit { prefs ->
            prefs[Keys.LAST_COMMIT_SHA] = sha
        }
    }

    suspend fun isFirstLaunch(): Boolean {
        val prefs = context.updateDataStore.data.firstOrNull() ?: return true
        val done = prefs[Keys.FIRST_LAUNCH_DONE]
        if (done == null) {
            context.updateDataStore.edit { p ->
                p[Keys.FIRST_LAUNCH_DONE] = "true"
            }
            return true
        }
        return false
    }
}