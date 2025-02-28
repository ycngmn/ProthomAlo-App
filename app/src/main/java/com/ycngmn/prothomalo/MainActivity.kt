package com.ycngmn.prothomalo

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ycngmn.prothomalo.ui.screens.HomePage
import com.ycngmn.prothomalo.ui.screens.NewsLecture
import com.ycngmn.prothomalo.ui.theme.ProthomAloTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            ProthomAloTheme(darkTheme = true) {

                NavHost(navController = navController, startDestination = "home") {
                    composable(
                        "home",
                        enterTransition = { EnterTransition.None },
                        popEnterTransition = { EnterTransition.None },
                    ) {
                        HomePage(navController)

                    }
                    composable("news/{url}",
                        arguments = listOf(navArgument("url") { type = NavType.StringType }),
                        enterTransition = { EnterTransition.None },
                        exitTransition = { fadeOut(animationSpec = tween(durationMillis = 300)) },
                        popExitTransition = { fadeOut(animationSpec = tween(durationMillis = 300)) }


                    ) { backStackEntry ->
                        val url = backStackEntry.arguments?.getString("url") ?: ""
                        NewsLecture(navController, Uri.encode(url))
                    }
                }

            }
        }
    }
}




