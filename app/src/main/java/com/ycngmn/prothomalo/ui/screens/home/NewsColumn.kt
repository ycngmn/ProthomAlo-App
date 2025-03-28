package com.ycngmn.prothomalo.ui.screens.home

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ycngmn.prothomalo.NewsViewModel
import com.ycngmn.prothomalo.scraper.ArticlesViewModel
import com.ycngmn.prothomalo.ui.animation.LoadingAnimation
import com.ycngmn.prothomalo.ui.components.ArticleCard_V1
import com.ycngmn.prothomalo.ui.components.ArticleCard_V2
import com.ycngmn.prothomalo.ui.screens.ErrorPage
import com.ycngmn.prothomalo.utils.ArticleEngine
import com.ycngmn.prothomalo.utils.PaloGlobal
import com.ycngmn.prothomalo.utils.rememberForeverLazyListState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsColumn(
    articlesVM: ArticlesViewModel,
    navController: NavController,
    newsViewModel: NewsViewModel,
    source: String = "home"
) {

    val articleEngine = ArticleEngine(articlesVM, PaloGlobal.getPalo())

    if (articlesVM.nbArticles.intValue == 0) {
        ErrorPage(navController)
        return
    }
    
    else if (articlesVM.articles.value.isEmpty()) {
        LoadingAnimation()
        articleEngine.loadArticles()
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
        if (isLoadMore && !articlesVM.isLimitReached) {
            articleEngine.loadMoreArticles()
        }
    }

    var isRefreshing by remember { mutableStateOf(false) }

    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = {
            isRefreshing = true
            articleEngine.refreshArticles()
        },
    ) {
        LazyColumn(state = listState) {

            val articles = articlesVM.articles.value
            itemsIndexed(articles) { index, article ->

                if (index % 15 == 0)
                    ArticleCard_V2(article) {
                        newsViewModel.setSection(articlesVM.getSection())
                        newsViewModel.updateUrls(articles.map { it.url })
                        navController.navigate("news/$index@$source")
                    }
                else
                    ArticleCard_V1(article) {
                        newsViewModel.setSection(articlesVM.getSection())
                        newsViewModel.updateUrls(articles.map { it.url })
                        navController.navigate("news/$index@$source")
                    }
            }

            if (!articlesVM.isLimitReached) {
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
}
