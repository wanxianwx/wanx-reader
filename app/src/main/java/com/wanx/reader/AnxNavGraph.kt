package com.wanx.reader

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.wanx.reader.ui.bookshelf.BookshelfScreen
import com.wanx.reader.ui.explore.ExploreScreen
import com.wanx.reader.ui.profile.ProfileScreen
import com.wanx.reader.ui.reader.ReaderScreen

/* ── 动画常量 ── */
private const val ANIM_DURATION = 300
private val animSpec = tween<Float>(ANIM_DURATION)

/**
 * 应用路由定义
 * 使用 sealed class 确保类型安全
 */
sealed class Screen(val route: String, val label: String) {
    data object Bookshelf : Screen("bookshelf", "书架")
    data object Explore : Screen("explore", "发现")
    data object Profile : Screen("profile", "我的")
    data object Reader : Screen("reader/{bookUrl}/{bookTitle}", "阅读") {
        fun createRoute(bookUrl: String, bookTitle: String): String =
            "reader/$bookUrl/$bookTitle"
    }
}

/** 所有底部导航路由列表 */
val allScreens = listOf(Screen.Bookshelf, Screen.Explore, Screen.Profile)

/**
 * 应用全局导航图
 *
 * 页面过渡动画：
 * - 底部导航 Tab 之间：fade 切换（无滑动，避免 Material 导航冲突）
 * - 进入阅读器：从右滑入 + 淡入
 * - 退出阅读器：向右滑出 + 淡出
 *
 * @param navController 导航控制器
 * @param modifier 修饰符
 */
@Composable
fun AnxNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Bookshelf.route,
        modifier = modifier,
        enterTransition = { fadeIn(animSpec) },
        exitTransition = { fadeOut(animSpec) },
    ) {
        /* ── 书架 ── */
        composable(
            route = Screen.Bookshelf.route,
            enterTransition = { fadeIn(animSpec) },
            exitTransition = { fadeOut(animSpec) },
        ) {
            BookshelfScreen(
                onBookClick = { bookUrl ->
                    navController.navigate(
                        Screen.Reader.createRoute(bookUrl, bookUrl)
                    )
                },
            )
        }

        /* ── 发现 ── */
        composable(
            route = Screen.Explore.route,
            enterTransition = { fadeIn(animSpec) },
            exitTransition = { fadeOut(animSpec) },
        ) {
            ExploreScreen()
        }

        /* ── 我的 ── */
        composable(
            route = Screen.Profile.route,
            enterTransition = { fadeIn(animSpec) },
            exitTransition = { fadeOut(animSpec) },
        ) {
            ProfileScreen()
        }

        /* ── 阅读器（从右侧滑入）── */
        composable(
            route = Screen.Reader.route,
            arguments = listOf(
                navArgument("bookUrl") { type = NavType.StringType },
                navArgument("bookTitle") { type = NavType.StringType },
            ),
            enterTransition = {
                slideInHorizontally(animSpec) { it / 3 } + fadeIn(animSpec)
            },
            exitTransition = {
                slideOutHorizontally(animSpec) { it / 3 } togetherWith
                    fadeOut(animSpec)
            },
        ) { backStackEntry ->
            val bookUrl = backStackEntry.arguments?.getString("bookUrl") ?: ""
            val bookTitle = backStackEntry.arguments?.getString("bookTitle") ?: ""
            ReaderScreen(
                bookTitle = bookTitle,
                bookUrl = bookUrl,
                onBack = { navController.popBackStack() },
            )
        }
    }
}