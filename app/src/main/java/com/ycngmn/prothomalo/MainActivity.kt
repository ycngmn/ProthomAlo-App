package com.ycngmn.prothomalo

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideOutHorizontally
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ycngmn.prothomalo.ui.screens.HomePage
import com.ycngmn.prothomalo.ui.screens.NewsLecture


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            NavHost(navController = navController, startDestination = "home") {
                composable("home") { HomePage(navController) }
                composable("news/{url}",
                    arguments = listOf(navArgument("url") { type = NavType.StringType }),
                    enterTransition = { fadeIn(animationSpec = tween(300)) },
                    exitTransition = { slideOutHorizontally(animationSpec = tween(300), targetOffsetX = { -it }) }

                ) { backStackEntry ->
                    val url = backStackEntry.arguments?.getString("url") ?: ""
                    NewsLecture(navController,Uri.encode(url))
                }
            }

        }
    }
}



