package com.ycngmn.prothomalo.ui.screens.bookmark

import android.content.Context
import android.widget.Toast
import com.ycngmn.prothomalo.Strings
import com.ycngmn.prothomalo.prothomalo.NewsContainer
import com.ycngmn.prothomalo.utils.downloadNewsImages
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

fun onBookmarkButtonClick(context: Context, newsContainer: NewsContainer, bookmarkDao: BookmarkDao) {
    val coroutineScope = CoroutineScope(Dispatchers.Main)

    coroutineScope.launch {
        val isExist = withContext(Dispatchers.IO) {
            bookmarkDao.checkBookmark(newsContainer.newsUrl)
        }

        if (isExist) {
            withContext(Dispatchers.IO) {
                bookmarkDao.deleteBookmark(newsContainer.newsUrl)
            }
            Toast.makeText(context, Strings.get("bookmark_delete_toast"), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, Strings.get("bookmark_save_toast"), Toast.LENGTH_SHORT).show()
            withContext(Dispatchers.IO) {
                bookmarkDao.insertBookmark(newsContainer.copy(readAlso = emptyList()))
                downloadNewsImages(newsContainer, context)
            }

        }
    }
}

