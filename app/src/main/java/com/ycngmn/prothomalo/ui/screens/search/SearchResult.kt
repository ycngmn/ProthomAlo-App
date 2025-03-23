package com.ycngmn.prothomalo.ui.screens.search


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
import com.ycngmn.prothomalo.scraper.ArticlesViewModel
import com.ycngmn.prothomalo.scraper.ShurjoFamily
import com.ycngmn.prothomalo.ui.screens.home.BottomBar
import com.ycngmn.prothomalo.ui.screens.home.NewsColumn
import kotlin.random.Random

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
        topBar = { TopicTopBar("Search Results") { navController.popBackStack("home", inclusive = false) } },
        bottomBar = { BottomBar(navController) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .background(MaterialTheme.colorScheme.background)
        ) {
            val articleVM = viewModel(key = searchViewModel.searchText) {
               ArticlesViewModel(Random.nextInt().toString(), searchViewModel = searchViewModel, isSearch = true)
            }

            NewsColumn(
                articlesVM = articleVM,
                navController = navController,
                newsViewModel = viewModel
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