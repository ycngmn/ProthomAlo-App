package com.ycngmn.prothomalo.ui.screens.bookmark

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.ycngmn.prothomalo.prothomalo.ArticleContainer
import com.ycngmn.prothomalo.prothomalo.NewsContainer
import com.ycngmn.prothomalo.ui.components.ArticleCard_V1
import com.ycngmn.prothomalo.ui.components.ArticleCard_V2
import com.ycngmn.prothomalo.ui.screens.article.NewsViewModel
import com.ycngmn.prothomalo.utils.FormatTime
import com.ycngmn.prothomalo.utils.SwipeToDeleteContainer
import com.ycngmn.prothomalo.utils.rememberForeverLazyListState


@Composable
fun BookmarkColumn(
    bookmarks: List<NewsContainer>,
    navController: NavController,
    newsViewModel: NewsViewModel,
    onDelete: (String) -> Unit
) {

    if (bookmarks.isNotEmpty() && bookmarks[0].headline == "") return

    if (bookmarks.isEmpty()) {
        EmptyBookmarkScreen { navController.navigateUp() }
        return
    }

    val articles = bookmarks.map {
        val thumbnail = it.body.firstOrNull { it.first == "image" }?.second ?: ""
        ArticleContainer(
            it.headline, thumbnail,
            url = it.newsUrl, date = FormatTime.toAgoString(it.date, it.newsUrl),
            subHead = "null")
    }

    val listState = rememberForeverLazyListState(key = "bookmark")

    LazyColumn(state = listState) {

        itemsIndexed(articles, key = { _, article -> article.url }) { index, article ->

            SwipeToDeleteContainer(onDelete = { onDelete(article.url) }) {

                if (index % 15 == 0)
                    ArticleCard_V2(article) {
                        newsViewModel.updateUrls(articles.map { it.url })
                        bookmarks.forEach { newsViewModel.updateNewsCache(it.newsUrl, it) }
                        navController.navigate("news/$index")
                    }
                else
                    ArticleCard_V1(article) {
                        newsViewModel.updateUrls(articles.map { it.url })
                        bookmarks.forEach { newsViewModel.updateNewsCache(it.newsUrl, it) }
                        navController.navigate("news/$index")
                    }
            }
        }

    }

}
