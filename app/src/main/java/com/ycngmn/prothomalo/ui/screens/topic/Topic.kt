package com.ycngmn.prothomalo.ui.screens.topic

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.ycngmn.prothomalo.prothomalo.PaloVM
import com.ycngmn.prothomalo.ui.screens.article.NewsViewModel
import com.ycngmn.prothomalo.ui.screens.home.BottomBar
import com.ycngmn.prothomalo.ui.screens.home.NewsColumn

@Composable
fun TopicScreen(navController: NavHostController, topicX: String, newsViewModel: NewsViewModel) {

    BackHandler {
        while (navController.navigateUp()) {
            if (navController.currentDestination?.route != "news/{index}")
                break
        }
    }

    var topicSlug = topicX.split("@").first()
    val topicText = topicX.split("@").last()

    val isTopic = topicSlug.startsWith("topic_") || topicText == topicSlug
    if (isTopic) topicSlug = topicSlug.replace("topic_", "")


    Scaffold (
        topBar = { TopicTopBar(topicText) {
            while (navController.navigateUp()) {
                if (navController.currentDestination?.route != "news/{index}")
                    break }
        }
                 },
        bottomBar = { BottomBar(navController) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .background(MaterialTheme.colorScheme.background)
        ) {
            val articleVM = viewModel(key = topicSlug) {
                PaloVM(topicSlug, isTopic = isTopic)
            }
            NewsColumn(
                articlesVM = articleVM,
                navController = navController,
                newsViewModel = newsViewModel
            )
        }
    }
}