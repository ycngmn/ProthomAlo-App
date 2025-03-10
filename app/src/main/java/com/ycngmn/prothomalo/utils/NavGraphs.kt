package com.ycngmn.prothomalo.utils

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
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
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ThemeViewModel(private val dataStoreManager: DataStoreManager) : ViewModel() {
    private val _theme = MutableStateFlow(0)
    val theme: StateFlow<Int> = _theme

    init {
        runBlocking { // wait to avoid premature theme load.
            _theme.value = dataStoreManager.themeState.first()
        }
    }

    fun toggleTheme(state: Int) {
        _theme.value = state
        viewModelScope.launch {
            dataStoreManager.saveThemeState(state)
        }
    }

}

class ThemeViewModelFactory(private val dataStoreManager: DataStoreManager) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ThemeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ThemeViewModel(dataStoreManager) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}



@Composable
fun MainNavGraph() {

    val navController = rememberNavController()
    val viewModel: NewsViewModel = viewModel()

    val context = LocalContext.current
    val dataStoreManager = remember { DataStoreManager(context) }

    val themeViewModel: ThemeViewModel = viewModel(factory = ThemeViewModelFactory(dataStoreManager))
    val theme by themeViewModel.theme.collectAsState()



    ProthomAloTheme(darkTheme = theme == 2 || (theme == 0 && isSystemInDarkTheme()) ) {
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
                "Menu",
                enterTransition = { EnterTransition.None },
                popEnterTransition = { EnterTransition.None },
            ) {
                MenuScreen(navController)
            }

            composable(
                "Settings",
                enterTransition = { EnterTransition.None },
                popEnterTransition = { EnterTransition.None },
            ) {
                ProfileScreen(themeViewModel, navController)
            }

        }
    }
}