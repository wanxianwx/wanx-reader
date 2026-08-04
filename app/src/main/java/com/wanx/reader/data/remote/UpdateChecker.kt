package com.wanx.reader.data.remote

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 自定义 Git 仓库更新检查器
 *
 * 替代原 Legado 访问 api.github.com 检查官方 Release 的逻辑。
 * 改为检查指定 Git 仓库（GitHub / Gitee）的最新 commit SHA。
 *
 * 使用方式：
 *   1. 首次启动时获取最新 commit SHA 并存入 DataStore
 *   2. 后续每次启动对比，若 SHA 不同则视为有更新
 *   3. 弹出 AlertDialog 提示用户去 Release 页面下载
 */
@Singleton
class UpdateChecker @Inject constructor() {

    private val client = OkHttpClient.Builder()
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(15, TimeUnit.SECONDS)
        .build()

    /**
     * 获取指定仓库的最新 commit SHA
     *
     * @param owner 仓库所有者
     * @param repo  仓库名称
     * @param useGitee 是否使用 Gitee API（国内镜像）
     * @return 最新 commit SHA，失败返回 null
     */
    suspend fun fetchLatestCommitSha(
        owner: String = "wanxianwx",
        repo: String = "wanx-reader",
        useGitee: Boolean = false,
    ): String? = withContext(Dispatchers.IO) {
        try {
            val apiUrl = if (useGitee) {
                "https://gitee.com/api/v5/repos/$owner/$repo/commits"
            } else {
                "https://api.github.com/repos/$owner/$repo/commits"
            }

            val request = Request.Builder()
                .url(apiUrl)
                .header("Accept", "application/vnd.github+json")
                .build()

            val response = client.newCall(request).execute()
            val body = response.body?.string() ?: return@withContext null

            if (!response.isSuccessful) return@withContext null

            /* 解析 JSON 数组，取第一条 commit 的 sha */
            val jsonArray = JSONArray(body)
            if (jsonArray.length() == 0) return@withContext null
            jsonArray.getJSONObject(0).getString("sha")
        } catch (e: Exception) {
            null /* 网络异常静默处理，不阻塞应用启动 */
        }
    }

    /**
     * 获取 Release 页面 URL
     *
     * @param owner 仓库所有者
     * @param repo  仓库名称
     */
    fun getReleaseUrl(
        owner: String = "wanxianwx",
        repo: String = "wanx-reader",
    ): String = "https://github.com/$owner/$repo/releases"
}