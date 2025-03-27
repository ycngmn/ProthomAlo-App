package com.ycngmn.prothomalo.ui.screens.bookmark

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.ycngmn.prothomalo.NewsViewModel
import com.ycngmn.prothomalo.scraper.ArticleContainer
import com.ycngmn.prothomalo.scraper.ArticlesViewModel
import com.ycngmn.prothomalo.scraper.NewsContainer
import com.ycngmn.prothomalo.ui.screens.home.BottomBar
import com.ycngmn.prothomalo.ui.screens.home.NewsColumn
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun BookmarkScreen(navController: NavController) {
    Scaffold (bottomBar = { BottomBar(navController) }) {
        Column (modifier = Modifier.padding(it)) {
            BookmarkList(navController)
        }
    }
}

@Composable
fun BookmarkList(navController: NavController) {

    val context = LocalContext.current
    val database = remember { BookmarkDatabaseHelper.getInstance(context) }
    val bookmarkDao = remember { database.bookmarkDao() }

    var bookmarks by remember { mutableStateOf<List<NewsContainer>>(emptyList()) }
    var isLoading by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        isLoading = true
        val fetchedBookmarks = withContext(Dispatchers.IO) { bookmarkDao.getBookmarks() }
        bookmarks = fetchedBookmarks
    }

    if (bookmarks.isEmpty())
        EmptyBookmarkScreen { navController.navigateUp() }
    else {
        val articles = bookmarks.map { ArticleContainer(it.headline) }
        val articleVM = viewModel(key = "bookmark") { ArticlesViewModel("bookmark") }
        articleVM.setArticles(articles)
        NewsColumn(articleVM, navController, NewsViewModel())
    }
}