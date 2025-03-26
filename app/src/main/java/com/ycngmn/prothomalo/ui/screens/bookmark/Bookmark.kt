package com.ycngmn.prothomalo.ui.screens.bookmark

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ycngmn.prothomalo.R
import com.ycngmn.prothomalo.scraper.NewsContainer
import com.ycngmn.prothomalo.scraper.ShurjoFamily
import com.ycngmn.prothomalo.ui.theme.PaloBlue
import com.ycngmn.prothomalo.ui.theme.PaloRed
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


@Composable
fun BookmarkScreen(navController: NavController) {

    val context = LocalContext.current
    val database = remember { BookmarkDatabaseHelper.getInstance(context) }
    val bookmarkDao = remember { database.bookmarkDao() }

    var bookmarks by remember { mutableStateOf<List<NewsContainer>>(emptyList()) }


    LaunchedEffect(Unit) {
        val fetchedBookmarks = withContext(Dispatchers.IO) { bookmarkDao.getBookmarks() }
        bookmarks = fetchedBookmarks
    }
    if (bookmarks.isEmpty()) {

        Column(
            modifier = Modifier.fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            val annotatedString = buildAnnotatedString {
                append("কোন খবর সংরক্ষণ করতে তার ওপর আলতো করে কিছুক্ষণ চেপে রাখুন। অথবা খবরের ভিতরে প্রবেশ করে \n")
                appendInlineContent(id = "imageId")
                append("চিহ্নতে স্পর্শ করুন।")
            }

            val inlineContentMap = mapOf(
                "imageId" to InlineTextContent(
                    Placeholder(40.sp, 40.sp, PlaceholderVerticalAlign.TextCenter)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.bookmark_icon),
                        contentDescription = "Bookmark article",
                        tint = PaloRed,
                    )
                }
            )


            Text(
                annotatedString,
                inlineContent = inlineContentMap,
                textAlign = TextAlign.Center,
                fontFamily = ShurjoFamily,
                fontSize = 25.sp,
                color = MaterialTheme.colorScheme.onBackground,
            )

            Spacer(Modifier.height(100.dp))

            Text(
                text = "বুঝেছি",
                textAlign = TextAlign.Center,
                fontFamily = ShurjoFamily,
                fontSize = 25.sp,
                textDecoration = TextDecoration.Underline,
                color = PaloBlue,
                modifier = Modifier.clickable { navController.navigateUp() }
            )

        }
    }

    else {
        Text(bookmarks.first().headline)
    }
}