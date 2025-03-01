package com.ycngmn.prothomalo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ycngmn.prothomalo.ui.screens.HomePage
import com.ycngmn.prothomalo.ui.screens.NewsLecture
import com.ycngmn.prothomalo.ui.screens.TopicScreen
import com.ycngmn.prothomalo.ui.theme.ProthomAloTheme


class NewsViewModel : ViewModel() {
    private var section = ""
    var newsUrls by mutableStateOf<List<String>>(emptyList())

    fun updateUrls(newUrls: List<String>) {
        newsUrls = newUrls
    }
    fun setSection(newSection: String) {
        section = newSection
    }
    fun getSection(): String {
        return section
    }
}



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val viewModel: NewsViewModel = viewModel()

            ProthomAloTheme(darkTheme = true) {

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
                        val index = backStackEntry.arguments?.getString("index")?.toIntOrNull() ?: 0
                        NewsLecture(navController, urlsVM = viewModel, startIndex = index)
                    }

                    composable("topic/{topicKey}",
                        arguments = listOf(navArgument("topicKey") { type = NavType.StringType }),
                        enterTransition = { EnterTransition.None },
                        exitTransition = { fadeOut(animationSpec = tween(durationMillis = 300)) },
                        popExitTransition = { fadeOut(animationSpec = tween(durationMillis = 300)) }
                    ) {
                        val topicKey = it.arguments?.getString("topicKey") ?: ""
                        TopicScreen(navController,topicKey, viewModel)
                    }
                }

            }
        }
    }
}




