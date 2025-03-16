package com.ycngmn.prothomalo.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.ycngmn.prothomalo.NewsViewModel
import com.ycngmn.prothomalo.scraper.ArticlesViewModel
import com.ycngmn.prothomalo.scraper.ProthomAlo

@Composable
fun HomePage(navController: NavController, newsViewModel: NewsViewModel) {

    val prothomAlo = remember { ProthomAlo() }
    val keys = remember { prothomAlo.articleSections.keys.toList() }
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { ProthomAlo().articleSections.size })

    Scaffold(
        topBar = {
            Surface(shadowElevation = 3.dp) {
                TopBar(pagerState)
            }
        },
        bottomBar = { BottomBar(navController) }
    )
    {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .background(MaterialTheme.colorScheme.background)
        ) {
            HorizontalPager(state = pagerState) { page ->
                val articleVM = viewModel(key = keys[page]) { ArticlesViewModel(keys[page]) }
                NewsColumn(articleVM, navController, newsViewModel)
            }
        }
    }
}