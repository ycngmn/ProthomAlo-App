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
import androidx.navigation.NavController
import com.ycngmn.prothomalo.NewsViewModel
import com.ycngmn.prothomalo.prothomalo.ArticleContainer
import com.ycngmn.prothomalo.prothomalo.NewsContainer
import com.ycngmn.prothomalo.ui.screens.home.BottomBar
import com.ycngmn.prothomalo.utils.loadSavedImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun BookmarkScreen(navController: NavController, viewModel: NewsViewModel) {
    Scaffold (bottomBar = { BottomBar(navController) }) {
        Column (modifier = Modifier.padding(it)) {
            BookmarkList(navController, viewModel)
        }
    }
}

@Composable
fun BookmarkList(navController: NavController, viewModel: NewsViewModel) {

    val context =  LocalContext.current
    val database = remember { BookmarkDatabaseHelper.getInstance(context) }
    val bookmarkDao = remember { database.bookmarkDao() }

    var bookmarks by remember { mutableStateOf<List<NewsContainer>>(emptyList()) }
    var isLoading by remember { mutableStateOf(false) }

    LaunchedEffect(bookmarkDao) {
        isLoading = true
        val fetchedBookmarks = withContext(Dispatchers.IO) { bookmarkDao.getBookmarks() }
        bookmarks = fetchedBookmarks
    }



    BookmarkColumn(context, bookmarks, navController, viewModel) {
    }
}