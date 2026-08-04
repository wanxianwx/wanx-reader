# Wanx Reader

> 基于 Jetpack Compose + Clean Architecture 的现代阅读器重构项目

[![Kotlin](https://img.shields.io/badge/Kotlin-2.1.0-blueviolet)](https://kotlinlang.org)
[![Compose](https://img.shields.io/badge/Jetpack%20Compose-BOM%202024.12.01-green)](https://developer.android.com/compose)
[![License](https://img.shields.io/badge/License-GPL%203.0-blue)](LICENSE)

---

## 📖 项目简介

**Wanx Reader** 是一个从开源阅读器 [Legado](https://github.com/gedoor/legado) 重构而来的现代化电子书阅读应用。

本项目保留了 Legado 的核心底层能力（书源解析、网络请求、数据库），同时将前端 UI 层完全替换为 **Jetpack Compose**，视觉风格参考 [Anx Reader](https://github.com/Anxcye/anx-reader)，追求极简、大圆角、高留白、现代扁平化的设计语言。

### 技术栈

| 层级 | 技术 |
|------|------|
| 语言 | Kotlin |
| UI 框架 | Jetpack Compose + Material 3 |
| 架构 | Clean Architecture（领域驱动分层） |
| 异步 | Kotlin Coroutines + Flow（StateFlow / SharedFlow） |
| 依赖注入 | Hilt |
| 图片加载 | Coil |
| 导航 | Compose Navigation |
| 网络 | OkHttp |
| 本地存储 | DataStore Preferences |

---

## 🚀 快速开始

### 环境要求

- Android Studio Hedgehog (2024.1.1) 或更新版本
- JDK 17+
- Android SDK 35+

### 构建

```bash
git clone https://github.com/wanxianwx/wanx-reader.git
cd wanx-reader
./gradlew assembleDebug
```

### 安装

```bash
adb install app/build/outputs/apk/debug/app-debug.apk
```

---

## 📁 项目结构

```
app/src/main/java/com/wanx/reader/
├── MainActivity.kt              # 应用入口
├── AnxNavGraph.kt               # 全局路由
├── WanxApplication.kt           # Hilt Application
│
├── data/
│   ├── local/
│   │   └── UpdatePreferences.kt # 更新检查偏好存储
│   ├── remote/
│   │   └── UpdateChecker.kt     # Git 仓库更新检查
│   └── repository/
│       └── BookRepository.kt    # 书籍数据仓库
│
├── domain/
│   └── model/
│       └── Book.kt              # 书籍领域模型
│
├── di/
│   └── AppModule.kt             # Hilt 依赖注入模块
│
└── ui/
    ├── theme/
    │   ├── Color.kt             # 色彩系统
    │   ├── Type.kt              # 排版系统
    │   ├── Theme.kt             # 全局主题
    │   ├── ThemeMode.kt         # 主题模式枚举
    │   ├── ThemeViewModel.kt    # 主题状态管理
    │   └── AnxGradientBackground.kt  # 呼吸感渐变背景
    │
    ├── component/
    │   ├── AnxTopAppBar.kt      # 顶部导航栏
    │   ├── AnxBottomBar.kt      # 底部导航栏
    │   ├── AnxRoundedCard.kt    # 圆角卡片
    │   └── UpdateDialog.kt      # 更新提示对话框
    │
    ├── bookshelf/               # 书架页面
    ├── explore/                 # 发现页面
    ├── profile/                 # 我的/设置页面
    └── reader/                  # 阅读器页面
```

---

## ⚠️ 免责声明

### 一、版权声明

本项目基于 [Legado](https://github.com/gedoor/legado)（GPL 3.0 协议）进行重构开发。**本项目不包含任何 Legado 的原始代码**，仅在其架构设计基础上使用 Jetpack Compose 重写了 UI 层，并按照 Clean Architecture 进行了分层重构。

本项目不包含任何图书内容、书源数据或版权素材。所有书籍封面图片、书名、作者等元数据均来自公开的第三方数据接口，**版权归原作者及出版社所有**。

### 二、使用限制

1. **仅供学习与研究**：本项目仅用于学习 Jetpack Compose、Clean Architecture 和 Android 现代开发技术，**严禁用于任何商业用途**。

2. **禁止用于侵权**：使用者不得利用本软件阅读、下载、传播任何未经授权的版权内容。使用本软件访问、获取、存储任何受版权保护的内容，使用者应自行确保已获得合法授权。

3. **责任自负**：使用者因使用本软件而产生的任何法律纠纷、版权争议、数据丢失或其他损失，**本项目开发者不承担任何责任**。

### 三、第三方内容

1. 本软件可能通过书源规则访问第三方网站或 API。**本软件不存储、不缓存、不审核任何第三方内容**，所有内容均来自使用者自行配置的书源。

2. 使用者应自行遵守其所在国家/地区的法律法规。**本软件开发者不支持、不鼓励、不纵容任何形式的盗版行为**。

### 四、技术免责

1. 本项目按"**原样（AS IS）**"提供，不提供任何明示或暗示的保证，包括但不限于适销性、特定用途适用性和非侵权性的保证。

2. 在任何情况下，**本项目的开发者、贡献者均不对任何直接、间接、附带、特殊、惩戒性或后果性损害（包括但不限于替代商品或服务的采购、使用损失、数据丢失或利润损失）承担责任**。

3. 本软件中的更新检查功能仅用于检测 Git 仓库的代码变更，**不会自动下载或安装任何 APK 文件**，用户需自行前往 Release 页面手动下载。

### 五、隐私声明

1. 本软件**不收集任何用户个人信息**，不包含任何埋点、统计或分析 SDK。

2. 更新检查功能仅发送 HTTP GET 请求到 GitHub / Gitee API 获取公开的 commit 数据，**不包含任何用户标识信息**。

3. 所有数据均存储在设备本地，**不会上传到任何服务器**。

### 六、免责声明变更

本免责声明可能随时更新，更新后的声明将在本仓库中发布。使用者应定期查阅以了解最新条款。

---

## 📄 许可证

本项目基于 [GNU General Public License v3.0](LICENSE) 开源。

```
Wanx Reader — Modern ebook reader built with Jetpack Compose
Copyright (C) 2025 Wanx Reader Contributors

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.
```

---

## 🙏 致谢

- [Legado](https://github.com/gedoor/legado) — 原始开源阅读器项目
- [Anx Reader](https://github.com/Anxcye/anx-reader) — UI 视觉设计参考
- [Jetpack Compose](https://developer.android.com/compose) — 现代化 Android UI 框架
- [Material Design 3](https://m3.material.io/) — 设计系统