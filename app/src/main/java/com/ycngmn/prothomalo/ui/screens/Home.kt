package com.ycngmn.prothomalo.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.ycngmn.prothomalo.NewsViewModel
import com.ycngmn.prothomalo.R
import com.ycngmn.prothomalo.scraper.ArticlesViewModel
import com.ycngmn.prothomalo.scraper.ProthomAlo
import com.ycngmn.prothomalo.scraper.ShurjoFamily
import com.ycngmn.prothomalo.ui.animation.LoadingAnimation
import com.ycngmn.prothomalo.ui.components.ArticleCard_V1
import com.ycngmn.prothomalo.ui.components.ArticleCard_V2
import com.ycngmn.prothomalo.utils.ArticleEngine
import com.ycngmn.prothomalo.utils.CustomScrollableTabRow
import com.ycngmn.prothomalo.utils.SetStatusBarColor
import com.ycngmn.prothomalo.utils.rememberForeverLazyListState
import com.ycngmn.prothomalo.utils.selectColorByIndex
import kotlinx.coroutines.launch

@Composable
fun HomePage(navController: NavController, newsViewModel: NewsViewModel) {

    SetStatusBarColor()

    val prothomAlo = remember { ProthomAlo() }
    val keys = remember { prothomAlo.articleSections.keys.toList() }
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { ProthomAlo().articleSections.size })

    Scaffold(
        topBar = {
            Surface(shadowElevation = 3.dp) {
                TopBar(pagerState)
            }
        },
        bottomBar = { BottomBar() }
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

@Composable
fun TopBar(pageState: PagerState) {

    val coroutineScope = rememberCoroutineScope()

    Column (
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.SpaceBetween
    )
    {
        Row {
            Box (modifier = Modifier.fillMaxWidth()) {
                Image(
                    painter = painterResource(R.drawable.main_logo_foreground),
                    contentDescription = "ProthomAlo_Logo",
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground)
                )
                Image(
                    painter = painterResource(R.drawable.main_logo_background),
                    contentDescription = "ProthomAlo_Logo_red",
                )

                Icon(
                    painterResource(R.drawable.profile_setting_icon),
                    contentDescription = "Account_and_Setting_logo",
                    modifier = Modifier.align(Alignment.CenterEnd)
                        .padding(end = 10.dp),
                )
            }

        }

        Spacer(modifier = Modifier.height(10.dp))

        val articleSections = ProthomAlo().articleSections.values.toList()

        CustomScrollableTabRow (
            pagerState = pageState,
            tabs = articleSections
        ) {
            coroutineScope.launch {
                pageState.animateScrollToPage(it)
            }

        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsColumn(
    articlesVM: ArticlesViewModel,
    navController: NavController,
    newsViewModel: NewsViewModel,
    source: String = "home"
) {

    if (articlesVM.articles.value.isEmpty()) {
        LoadingAnimation()
        ArticleEngine(articlesVM).loadArticles()
        return
    }

    val listState = rememberForeverLazyListState(key = articlesVM.getSection())

    val isLoadMore by remember(articlesVM.offset) {
        derivedStateOf {
            val lastVisibleItem = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            lastVisibleItem >= articlesVM.articles.value.size - 5 && articlesVM.articles.value.isNotEmpty()
        }
    }

    LaunchedEffect(isLoadMore) {
        if (isLoadMore)
            ArticleEngine(articlesVM).loadMoreArticles()
    }

    var isRefreshing by remember { mutableStateOf(false) }

    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = {
            isRefreshing = true
            ArticleEngine(articlesVM).refreshArticles()
        },
    ) {
        LazyColumn(state = listState) {

            itemsIndexed(articlesVM.articles.value) { index, article ->


                if (index % 15 == 0)
                    ArticleCard_V2(article) {
                        newsViewModel.setSection(articlesVM.getSection())
                        newsViewModel.updateUrls(articlesVM.articles.value.map { it.url })
                        navController.navigate("news/$index@$source")
                    }
                else
                    ArticleCard_V1(article) {
                        newsViewModel.setSection(articlesVM.getSection())
                        newsViewModel.updateUrls(articlesVM.articles.value.map { it.url })
                        navController.navigate("news/$index@$source")
                    }
            }

            item {
                LinearProgressIndicator(
                    modifier = Modifier
                        .padding(horizontal = 20.dp, vertical = 35.dp).fillMaxWidth(),
                    trackColor = MaterialTheme.colorScheme.background,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}


data class BottomNavItem(
    val route: String,
    val icon: Int,
    val label: String
)

@Composable
fun BottomBar() {

    val sections = listOf(
        BottomNavItem("Home", R.drawable.pa_icon, "Home"),
        BottomNavItem("Explore", R.drawable.discover_icon, "Explore"),
        BottomNavItem("Bookmark", R.drawable.bookmark_icon, "Bookmark"),
        BottomNavItem("Menu", R.drawable.menu_icon, "Menu")
    )

    var selectedItem by remember { mutableIntStateOf(0) }

    Surface (modifier = Modifier.fillMaxWidth(), shadowElevation = 10.dp) {
        NavigationBar(
            containerColor = MaterialTheme.colorScheme.background,
            modifier = Modifier
                .shadow(50.dp)
                .height(60.dp)
        ) {
            sections.forEachIndexed { index, item ->
                NavigationBarItem(
                    icon = {
                        Icon(
                            painterResource(item.icon),
                            contentDescription = item.label,
                            modifier = Modifier.size(25.dp),
                        )
                    },

                    label = {
                        Text(
                            item.label, fontSize = 8.sp,
                            fontFamily = ShurjoFamily, fontWeight = FontWeight.Bold
                        )
                    },
                    selected = selectedItem == index,
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = selectColorByIndex(index),
                        selectedTextColor = selectColorByIndex(index),
                        indicatorColor = MaterialTheme.colorScheme.background,
                        unselectedIconColor = Color.Gray,
                        unselectedTextColor = Color.Gray,
                    ),
                    onClick = { selectedItem = index },

                    )
            }


        }
    }
}