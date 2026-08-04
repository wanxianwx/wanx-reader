package com.wanx.reader

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.material.icons.outlined.LibraryBooks
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.wanx.reader.ui.component.AnxBottomBar
import com.wanx.reader.ui.component.AnxBottomNavItem
import com.wanx.reader.ui.component.AnxTopAppBar
import com.wanx.reader.ui.component.UpdateDialog
import com.wanx.reader.ui.theme.AnxGradientBackground
import com.wanx.reader.ui.theme.AnxTheme
import com.wanx.reader.ui.theme.ThemeViewModel
import com.wanx.reader.ui.UpdateViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WanxApp()
        }
    }
}

@Composable
private fun WanxApp() {
    val themeViewModel: ThemeViewModel = hiltViewModel()
    val themeMode by themeViewModel.themeMode.collectAsStateWithLifecycle()

    val updateViewModel: UpdateViewModel = hiltViewModel()
    val showUpdateDialog by updateViewModel.showUpdateDialog.collectAsStateWithLifecycle()
    val releaseUrl by updateViewModel.releaseUrl.collectAsStateWithLifecycle()
    val context = LocalContext.current

    AnxTheme(themeMode = themeMode) {
        AnxGradientBackground {
            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route ?: Screen.Bookshelf.route

            val navItems = remember {
                listOf(
                    AnxBottomNavItem("书架", Icons.Outlined.LibraryBooks, Screen.Bookshelf.route),
                    AnxBottomNavItem("发现", Icons.Outlined.Explore, Screen.Explore.route),
                    AnxBottomNavItem("我的", Icons.Outlined.Person, Screen.Profile.route),
                )
            }

            val currentTitle = remember(currentRoute) {
                allScreens.find { it.route == currentRoute }?.label ?: "Wanx Reader"
            }

            val showBottomBar = currentRoute != Screen.Reader.route.substringBefore("/{")

            Scaffold(
                topBar = {
                    if (showBottomBar) {
                        AnxTopAppBar(title = currentTitle)
                    }
                },
                bottomBar = {
                    if (showBottomBar) {
                        AnxBottomBar(
                            items = navItems,
                            currentRoute = currentRoute,
                            onItemClick = { route ->
                                navController.navigate(route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                        )
                    }
                },
            ) { innerPadding ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                ) {
                    AnxNavGraph(navController = navController)
                }
            }
        }

        /* 更新提示对话框 — 在 AnxTheme 内部，AnxGradientBackground 外部 */
        if (showUpdateDialog) {
            UpdateDialog(
                releaseUrl = releaseUrl,
                onDismiss = { updateViewModel.dismissUpdateDialog() },
                onGoDownload = { url ->
                    updateViewModel.dismissUpdateDialog()
                    context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                },
            )
        }
    }
}