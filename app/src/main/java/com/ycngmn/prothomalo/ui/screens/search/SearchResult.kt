package com.ycngmn.prothomalo.ui.screens.search


import com.ycngmn.prothomalo.Strings
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
import com.ycngmn.prothomalo.ui.screens.topic.TopicTopBar

@Composable
fun SearchResultScreen(
    navController: NavHostController,
    searchViewModel: SearchViewModel,
    viewModel: NewsViewModel
) {

    BackHandler {
        while (navController.navigateUp()) {
            if (navController.currentDestination?.route != "news/{index}")
                break
        }
    }

    Scaffold (
        topBar = {
            TopicTopBar(Strings.get("search_title"))
            { navController.navigateUp() } },
        bottomBar = { BottomBar(navController) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .background(MaterialTheme.colorScheme.background)
        ) {
            val articleVM = viewModel(key = searchViewModel.searchText) {
                PaloVM(searchViewModel.searchText, searchVM = searchViewModel, isSearch = true)
            }

            NewsColumn(
                articlesVM = articleVM,
                navController = navController,
                newsViewModel = viewModel,
            )
        }
    }
}