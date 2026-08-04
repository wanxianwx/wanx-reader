package com.wanx.reader

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
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

private const val ANIM_DURATION = 300

sealed class Screen(val route: String, val label: String) {
    data object Bookshelf : Screen("bookshelf", "书架")
    data object Explore : Screen("explore", "发现")
    data object Profile : Screen("profile", "我的")
    data object Reader : Screen("reader/{bookUrl}/{bookTitle}", "阅读") {
        fun createRoute(bookUrl: String, bookTitle: String): String =
            "reader/$bookUrl/$bookTitle"
    }
}

val allScreens = listOf(Screen.Bookshelf, Screen.Explore, Screen.Profile)

@Composable
fun AnxNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Bookshelf.route,
        modifier = modifier,
        enterTransition = { fadeIn(tween(ANIM_DURATION)) },
        exitTransition = { fadeOut(tween(ANIM_DURATION)) },
    ) {
        composable(
            route = Screen.Bookshelf.route,
            enterTransition = { fadeIn(tween(ANIM_DURATION)) },
            exitTransition = { fadeOut(tween(ANIM_DURATION)) },
        ) {
            BookshelfScreen(
                onBookClick = { bookUrl ->
                    navController.navigate(
                        Screen.Reader.createRoute(bookUrl, bookUrl)
                    )
                },
            )
        }

        composable(
            route = Screen.Explore.route,
            enterTransition = { fadeIn(tween(ANIM_DURATION)) },
            exitTransition = { fadeOut(tween(ANIM_DURATION)) },
        ) {
            ExploreScreen()
        }

        composable(
            route = Screen.Profile.route,
            enterTransition = { fadeIn(tween(ANIM_DURATION)) },
            exitTransition = { fadeOut(tween(ANIM_DURATION)) },
        ) {
            ProfileScreen()
        }

        composable(
            route = Screen.Reader.route,
            arguments = listOf(
                navArgument("bookUrl") { type = NavType.StringType },
                navArgument("bookTitle") { type = NavType.StringType },
            ),
            enterTransition = {
                slideInHorizontally(tween(ANIM_DURATION)) { it / 3 } + fadeIn(tween(ANIM_DURATION))
            },
            exitTransition = {
                slideOutHorizontally(tween(ANIM_DURATION)) { it / 3 } + fadeOut(tween(ANIM_DURATION))
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