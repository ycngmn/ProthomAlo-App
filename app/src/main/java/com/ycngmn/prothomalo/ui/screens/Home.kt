package com.ycngmn.prothomalo.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.ycngmn.prothomalo.R
import com.ycngmn.prothomalo.scraper.ArticlesViewModel
import com.ycngmn.prothomalo.scraper.ProthomAlo
import com.ycngmn.prothomalo.scraper.ShurjoFamily
import com.ycngmn.prothomalo.ui.animation.LoadingAnimation
import com.ycngmn.prothomalo.ui.components.ArticleCard_V1
import com.ycngmn.prothomalo.utils.ArticleEngine
import com.ycngmn.prothomalo.utils.rememberForeverLazyListState
import kotlinx.coroutines.launch


@Composable
fun HomePage(navController: NavController) {

    val articleVMs = remember {
        mutableMapOf<String, ArticlesViewModel>()
    }
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { ProthomAlo().articleSections.size })

    Scaffold (
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
                .background(Color.White)
        ) {

            HorizontalPager(state = pagerState) { page ->

                val key = ProthomAlo().articleSections.keys.toList()[page]
                Log.d("fuck", key)

                val articleVM = articleVMs.getOrPut(key) {
                    @Suppress("UNCHECKED_CAST")
                    viewModel(
                        key = key,
                        factory = object : ViewModelProvider.Factory {
                            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                                return ArticlesViewModel(key) as T
                            }
                        }

                    )
                }

                NewsColumn(articleVM, navController)
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
            .background(Color.White),
        verticalArrangement = Arrangement.SpaceBetween
    )
    {
        Row (
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        )
        {
            Image(
                painter = painterResource(R.drawable.main_logo),
                contentDescription = "ProthomAlo_Logo_White",
            )

            Spacer(modifier = Modifier.weight(1f))

            Icon(
                painterResource(R.drawable.profile_setting_icon),
                contentDescription = "Account_and_Setting_logo",
                modifier = Modifier.size(35.dp)

            )

        }

        val articleSections = ProthomAlo().articleSections.keys.toList()

        ScrollableTabRow (
            edgePadding = 2.dp,
            containerColor = Color.White,
            selectedTabIndex = pageState.currentPage) {
            articleSections.forEachIndexed { index, value ->

                Tab(
                    selected = pageState.currentPage == index,
                    onClick = {
                        coroutineScope.launch {
                            pageState.animateScrollToPage(index)
                        }
                    },
                    text = {
                        Text(
                            text = ProthomAlo().articleSections[value] ?: "",
                            color = Color.Black,
                            fontFamily = ShurjoFamily,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal,)
                    }
                )

            }
        }

    }
}

@Composable
fun NewsColumn(
    articlesVM : ArticlesViewModel,
    navController : NavController
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
            lastVisibleItem >= articlesVM.offset.value - 5 && articlesVM.articles.value.isNotEmpty()
        }
    }

    LaunchedEffect(isLoadMore) {
        if (isLoadMore)
            ArticleEngine(articlesVM).loadMoreArticles()
    }

    Column {

        LazyColumn(state = listState) {

            items(articlesVM.articles.value) { article ->
                ArticleCard_V1(article, navController)
            }

            item {
                LinearProgressIndicator(
                    modifier = Modifier
                        .padding(horizontal = 20.dp, vertical = 35.dp).fillMaxWidth(),
                    trackColor = Color.White,
                    color = Color.Black
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

    //val selectedItem = remember { mutableIntStateOf(0) }

    val sections = listOf(
        BottomNavItem("Home", R.drawable.pa_icon, "Home"),
        BottomNavItem("Explore", R.drawable.discover_icon, "Explore"),
        BottomNavItem("Bookmark", R.drawable.bookmark_icon, "Bookmark"),
        BottomNavItem("Menu", R.drawable.menu_icon, "Menu")
    )

    BottomAppBar(
        containerColor = Color.White,
        modifier = Modifier
            .shadow(20.dp)
            .height(65.dp)
    ) {

        Row (
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        )
        {
            for (section in sections) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .clickable(onClick = {})
                        .padding(8.dp)

                ) {
                    Icon(
                        modifier = Modifier.size(25.dp),
                        painter = painterResource(id = section.icon),
                        contentDescription = section.label)
                    Text(
                        modifier = Modifier.padding(top = 4.dp),
                        text = section.label, fontSize = 8.sp, color = Color.Gray,
                        fontFamily = ShurjoFamily, fontWeight = FontWeight.Bold)
                }
            }
        }
    }

}