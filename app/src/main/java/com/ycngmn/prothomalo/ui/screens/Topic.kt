package com.ycngmn.prothomalo.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.ycngmn.prothomalo.NewsViewModel
import com.ycngmn.prothomalo.R
import com.ycngmn.prothomalo.scraper.ArticlesViewModel
import com.ycngmn.prothomalo.scraper.ShurjoFamily

@Composable
fun TopicScreen(navController: NavHostController, topicX: String, newsViewModel: NewsViewModel) {

    val topic = topicX.split("@").first()
    val topicText = topicX.split("@").last()

    BackHandler {
        navController.popBackStack("home", inclusive = false)
    }


    Scaffold (
        topBar = { TopicTopBar(topicText) },
        bottomBar = { BottomBar() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .background(MaterialTheme.colorScheme.background)
        ) {
            val articleVM = viewModel(key = topic) { ArticlesViewModel(topic) }
            NewsColumn(
                articlesVM = articleVM                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            ,
                navController = navController,
                newsViewModel = newsViewModel
            )
        }
    }
}

@Composable
fun TopicTopBar(topic: String) {
    Row (
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box (modifier = Modifier.fillMaxWidth().padding(10.dp)) {

            Icon(
                Icons.AutoMirrored.Rounded.ArrowBack,
                contentDescription = "Account_and_Setting_logo",
                modifier = Modifier.size(28.dp).align(Alignment.CenterStart)
            )

            Text(
                modifier = Modifier.align(Alignment.Center),
                text = topic,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = ShurjoFamily,
                color = Color(0XFFD60000)
            )

            Icon(
                painterResource(R.drawable.profile_setting_icon),
                contentDescription = "Account_and_Setting_logo",
                modifier = Modifier.size(35.dp).align(Alignment.CenterEnd)
            )
        }

    }
}