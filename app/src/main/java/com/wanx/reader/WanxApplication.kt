package com.wanx.reader

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Hilt 应用入口
 * 必须用 @HiltAndroidApp 标注 Application 才能触发 Hilt 代码生成
 */
@HiltAndroidApp
class WanxApplication : Application()