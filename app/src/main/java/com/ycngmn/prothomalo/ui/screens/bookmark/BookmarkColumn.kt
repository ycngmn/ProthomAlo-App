package com.ycngmn.prothomalo.ui.screens.bookmark

import android.content.Context
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import com.ycngmn.prothomalo.NewsViewModel
import com.ycngmn.prothomalo.prothomalo.ArticleContainer
import com.ycngmn.prothomalo.prothomalo.NewsContainer
import com.ycngmn.prothomalo.ui.components.ArticleCard_V1
import com.ycngmn.prothomalo.ui.components.ArticleCard_V2
import com.ycngmn.prothomalo.utils.loadSavedImage
import com.ycngmn.prothomalo.utils.rememberForeverLazyListState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookmarkColumn(
    context: Context,
    bookmarks: List<NewsContainer>,
    navController: NavController,
    newsViewModel: NewsViewModel,
    onRefresh: () -> Unit
) {

    val articles = bookmarks.map {
        val subDir = it.newsUrl.substringAfterLast("/")
        val thumbnail = loadSavedImage(context, subDir, "0.png") ?: ""
        ArticleContainer(it.headline, thumbnail, url = it.newsUrl, date = it.date, subHead = "null")
    }

    if (articles.isEmpty()) {
        EmptyBookmarkScreen { navController.navigateUp() }
        return
    }

    val listState = rememberForeverLazyListState(key = "bookmark")

    var isRefreshing by remember { mutableStateOf(false) }

    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = {
            isRefreshing = true
            onRefresh()
        },
    ) {
        LazyColumn(state = listState) {

            itemsIndexed(articles) { index, article ->

                if (index % 15 == 0)
                    ArticleCard_V2(article) {
                        newsViewModel.updateUrls(articles.map { it.url })
                        bookmarks.forEach { newsViewModel.updateNewsCache(it.newsUrl, it) }
                        navController.navigate("news/$index@bookmark")
                    }
                else
                    ArticleCard_V1(article) {
                        newsViewModel.updateUrls(articles.map { it.url })
                        bookmarks.forEach { newsViewModel.updateNewsCache(it.newsUrl, it) }
                        navController.navigate("news/$index@bookmark")
                    }
            }

        }
    }
}
