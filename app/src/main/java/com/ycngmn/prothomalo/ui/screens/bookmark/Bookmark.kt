package com.ycngmn.prothomalo.ui.screens.bookmark

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.ycngmn.prothomalo.NewsViewModel
import com.ycngmn.prothomalo.prothomalo.NewsContainer
import com.ycngmn.prothomalo.ui.screens.home.BottomBar
import com.ycngmn.prothomalo.utils.deleteSavedImages
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun BookmarkScreen(navController: NavController, newsVM: NewsViewModel, bookmarkDao: BookmarkDao) {
    Scaffold (bottomBar = { BottomBar(navController) }) {
        Column (modifier = Modifier.padding(it)) {
            BookmarkList(navController, newsVM, bookmarkDao)
        }
    }
}

@Composable
fun BookmarkList(navController: NavController, newsVM: NewsViewModel, bookmarkDao: BookmarkDao) {

    val bookmarks by bookmarkDao.getBookmarksFlow()
        .collectAsState(initial = listOf(NewsContainer()))

    val context = LocalContext.current
    BookmarkColumn(bookmarks, navController, newsVM) {
        val coroutineScope = CoroutineScope(Dispatchers.Main)
        coroutineScope.launch(Dispatchers.IO) {
            bookmarkDao.deleteBookmark(it)
            deleteSavedImages(context, it.substringAfterLast("/"))
        }
    }
}