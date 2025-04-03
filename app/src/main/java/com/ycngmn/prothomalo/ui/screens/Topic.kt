package com.ycngmn.prothomalo.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.ycngmn.prothomalo.NewsViewModel
import com.ycngmn.prothomalo.prothomalo.PaloVM
import com.ycngmn.prothomalo.prothomalo.ShurjoFamily
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

@Composable
fun TopicTopBar(topic: String, onBackPressed : () -> Unit) {
    Surface (
        modifier = Modifier.background(MaterialTheme.colorScheme.background),
        shadowElevation = 4.dp
    ) {
        Box (modifier = Modifier.fillMaxWidth().padding(10.dp)) {

            Icon(
                Icons.AutoMirrored.Rounded.ArrowBack,
                contentDescription = "Account_and_Setting_logo",
                modifier = Modifier.size(28.dp).align(Alignment.CenterStart)
                    .clickable { onBackPressed() }
            )

            val bg = MaterialTheme.colorScheme.onBackground
            Text(
                modifier = Modifier.align(Alignment.Center).padding(horizontal = 40.dp)
                    .drawBehind {
                    drawLine(
                        color = bg,
                        start = Offset(0f, size.height+6),
                        end = Offset(size.width, size.height+6),
                        strokeWidth = 4f
                    ) },
                text = topic,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = ShurjoFamily,
                color = MaterialTheme.colorScheme.onBackground,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}