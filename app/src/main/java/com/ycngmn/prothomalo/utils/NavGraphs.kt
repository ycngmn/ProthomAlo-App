package com.ycngmn.prothomalo.utils

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ycngmn.prothomalo.NewsViewModel
import com.ycngmn.prothomalo.ui.screens.NewsLecture
import com.ycngmn.prothomalo.ui.screens.ProfileScreen
import com.ycngmn.prothomalo.ui.screens.TopicScreen
import com.ycngmn.prothomalo.ui.screens.home.HomePage
import com.ycngmn.prothomalo.ui.screens.menu.MenuScreen
import com.ycngmn.prothomalo.ui.theme.ProthomAloTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


class ThemeViewModel : ViewModel() {
    private val _isDarkTheme = MutableStateFlow(false)
    val isDarkTheme: StateFlow<Boolean> = _isDarkTheme

    fun toggleTheme() {
        _isDarkTheme.value = !_isDarkTheme.value
    }
}


@Composable
fun MainNavGraph() {

    val navController = rememberNavController()
    val viewModel: NewsViewModel = viewModel()

    val themeViewModel: ThemeViewModel = viewModel()
    val isDarkTheme by themeViewModel.isDarkTheme.collectAsState()

    ProthomAloTheme(darkTheme = isDarkTheme) {
        SetStatusBarColor()
        NavHost(navController = navController, startDestination = "home") {
            composable(
                "home",
                enterTransition = { EnterTransition.None },
                popEnterTransition = { EnterTransition.None },
            ) {
                HomePage(navController, viewModel)
            }
            composable("news/{index}",
                arguments = listOf(navArgument("index") { type = NavType.StringType }),
                enterTransition = { EnterTransition.None },
                exitTransition = { fadeOut(animationSpec = tween(durationMillis = 300)) },
                popExitTransition = { fadeOut(animationSpec = tween(durationMillis = 300)) }


            ) { backStackEntry ->
                val str = backStackEntry.arguments?.getString("index")
                val index = str?.split("@")?.first()?.toIntOrNull() ?: 0
                val source = str?.split("@")?.last() ?: "home"
                NewsLecture(
                    navController,
                    urlsVM = viewModel,
                    startIndex = index,
                    navSource = source
                )
            }

            composable("topic/{topicKey}",
                arguments = listOf(navArgument("topicKey") { type = NavType.StringType }),
                enterTransition = { EnterTransition.None },
                exitTransition = { fadeOut(animationSpec = tween(durationMillis = 300)) },
                popExitTransition = { fadeOut(animationSpec = tween(durationMillis = 300)) }
            ) {
                val topicKey = it.arguments?.getString("topicKey") ?: ""
                TopicScreen(navController, topicKey, viewModel)
            }

            composable(
                "Profile",
                enterTransition = { EnterTransition.None },
                popEnterTransition = { EnterTransition.None },
            ) { ProfileScreen(themeViewModel) }

            composable(
                "Menu",
                enterTransition = { EnterTransition.None },
                popEnterTransition = { EnterTransition.None },
            ) {
                MenuScreen(navController)
            }

        }
    }
}