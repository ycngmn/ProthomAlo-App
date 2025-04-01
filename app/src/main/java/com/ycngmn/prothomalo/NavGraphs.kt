package com.ycngmn.prothomalo

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ycngmn.prothomalo.prothomalo.PaloGlobal
import com.ycngmn.prothomalo.ui.screens.TopicScreen
import com.ycngmn.prothomalo.ui.screens.article.NewsLecture
import com.ycngmn.prothomalo.ui.screens.bookmark.BookmarkScreen
import com.ycngmn.prothomalo.ui.screens.home.HomePage
import com.ycngmn.prothomalo.ui.screens.menu.MenuScreen
import com.ycngmn.prothomalo.ui.screens.search.SearchResultScreen
import com.ycngmn.prothomalo.ui.screens.search.SearchViewModel
import com.ycngmn.prothomalo.ui.screens.settings.SettingScreen
import com.ycngmn.prothomalo.ui.screens.settings.SettingsVM
import com.ycngmn.prothomalo.ui.screens.settings.SettingsVMFactory
import com.ycngmn.prothomalo.ui.theme.ProthomAloTheme
import com.ycngmn.prothomalo.utils.DataStoreManager
import com.ycngmn.prothomalo.utils.SetStatusBarColor

@Composable
fun MainNavGraph() {

    val navController = rememberNavController()
    val viewModel: NewsViewModel = viewModel()
    val searchViewModel: SearchViewModel = viewModel()

    val context = LocalContext.current
    val dataStoreManager = remember { DataStoreManager(context) }

    val settingsVM: SettingsVM = viewModel(factory = SettingsVMFactory(dataStoreManager))
    val theme by settingsVM.theme.collectAsState()
    val paloKey by settingsVM.paloKey.collectAsState()

    PaloGlobal.paloKey = paloKey
    PaloGlobal.isDarkTheme = theme == 2 || (theme == 0 && isSystemInDarkTheme())

    ProthomAloTheme(darkTheme = theme == 2 || (theme == 0 && isSystemInDarkTheme()) ) {
        SetStatusBarColor()
        NavHost(navController = navController, startDestination = "home") {
            composable(
                "home",
                enterTransition = { EnterTransition.None },
                popEnterTransition = { EnterTransition.None },
                exitTransition = { ExitTransition.None },
                popExitTransition = { ExitTransition.None }
            ) {
                HomePage(navController, viewModel)
            }
            composable("news/{index}",
                enterTransition = { EnterTransition.None },
                exitTransition = { fadeOut(animationSpec = tween(durationMillis = 300)) },
                popExitTransition = { fadeOut(animationSpec = tween(durationMillis = 300)) }


            ) { backStackEntry ->
                val str = backStackEntry.arguments?.getString("index")
                val index = str?.split("@")?.first()?.toIntOrNull() ?: 0
                NewsLecture(
                    navController,
                    urlsVM = viewModel,
                    startIndex = index,
                    settingsVM.isSeeMoreEnabled.value
                )
            }

            composable(
                route = "topic/{topicKey}",
                arguments = listOf(navArgument("topicKey") { type = NavType.StringType }),
                enterTransition = { EnterTransition.None },
                exitTransition = { fadeOut(animationSpec = tween(durationMillis = 300)) },
                popExitTransition = { fadeOut(animationSpec = tween(durationMillis = 300)) }
            ) {
                val topicKey = it.arguments?.getString("topicKey") ?: ""
                TopicScreen(navController, topicKey, viewModel)
            }
            composable(
                route = "Menu",
                enterTransition = { EnterTransition.None },
                popEnterTransition = { EnterTransition.None },
                exitTransition = { ExitTransition.None },
                popExitTransition = { ExitTransition.None }
            ) { MenuScreen(navController,searchViewModel) }

            composable(
                route = "Settings",
                enterTransition = { EnterTransition.None },
                popEnterTransition = { EnterTransition.None },
                exitTransition = { ExitTransition.None },
                popExitTransition = { ExitTransition.None }
            ) { SettingScreen(settingsVM, navController) }

            composable(
                route = "Bookmark",
                enterTransition = { EnterTransition.None },
                popEnterTransition = { EnterTransition.None },
                exitTransition = { ExitTransition.None },
                popExitTransition = { ExitTransition.None }
            ) { BookmarkScreen(navController, viewModel ) }

            composable(
                route = "search",
                enterTransition = { EnterTransition.None },
                popEnterTransition = { EnterTransition.None },
                exitTransition = { ExitTransition.None },
                popExitTransition = { ExitTransition.None }
            ) { SearchResultScreen(navController, searchViewModel, viewModel) }

        }
    }
}