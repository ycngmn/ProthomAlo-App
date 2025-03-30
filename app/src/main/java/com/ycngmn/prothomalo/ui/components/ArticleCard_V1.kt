package com.ycngmn.prothomalo.ui.components

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.ycngmn.prothomalo.R
import com.ycngmn.prothomalo.prothomalo.ArticleContainer
import com.ycngmn.prothomalo.prothomalo.ProthomAlo
import com.ycngmn.prothomalo.prothomalo.ShurjoFamily
import com.ycngmn.prothomalo.ui.screens.bookmark.BookmarkDatabaseHelper
import com.ycngmn.prothomalo.ui.theme.PaloRed
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


fun titleBuilder(subHead: String, title: String, subColor: Color, titleColor: Color): AnnotatedString {
    return buildAnnotatedString {
        if (subHead != "null") {
            withStyle(
                style = SpanStyle(color = subColor)
            ) { append(subHead) }

            withStyle(SpanStyle(color = Color.Gray, fontSize = 14.sp)) {
                append(" ● ")
            }
        }
        withStyle(
            SpanStyle(color = titleColor)
        ) { append(title) }

    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ArticleCard_V1(
    article: ArticleContainer,
    clickAction: () -> Unit,
) {
    val context = LocalContext.current
    val database = BookmarkDatabaseHelper.getInstance(context)
    val bookmarkDao = database.bookmarkDao()
    val coroutineScope = rememberCoroutineScope()
    var isBookmarkSuccess by remember { mutableStateOf(false) }
    var isBookmarkDelete by remember { mutableStateOf(false) }

    Column(modifier = Modifier.background(MaterialTheme.colorScheme.background)
        .combinedClickable(
            onClick = clickAction,
            onLongClick =  {
                coroutineScope.launch(Dispatchers.IO) {
                    val isExist = bookmarkDao.checkBookmark(article.url)

                    if (isExist) {
                        bookmarkDao.deleteBookmark(article.url)
                        isBookmarkDelete = true
                    } else {
                        val result = ProthomAlo().getNews(article.url)
                        bookmarkDao.insertBookmark(result)
                        isBookmarkSuccess = true
                    }

                    withContext(Dispatchers.Main) {
                        if (isBookmarkDelete) {
                            Toast.makeText(context, "নিবন্ধটি সফলভাবে মুছে ফেলা হয়েছে", Toast.LENGTH_SHORT).show()
                            isBookmarkDelete = false
                        }
                        if (isBookmarkSuccess) {
                            Toast.makeText(context, "নিবন্ধটি সফলভাবে সংরক্ষণ করা হয়েছে", Toast.LENGTH_SHORT).show()
                            isBookmarkSuccess = false
                        }
                    }
                }
            }
        )
    ) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {

            Text(
                text = titleBuilder(article.subHead, article.title, PaloRed, MaterialTheme.colorScheme.onBackground),
                modifier = Modifier.padding(start = 20.dp, top = 20.dp).weight(0.8f),
                fontFamily = ShurjoFamily,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )

            Box (modifier = Modifier.width(150.dp)
                .height(100.dp)
                .padding(start = 10.dp, top = 20.dp, end = 20.dp)) {

                Image(
                    painter = rememberAsyncImagePainter(article.thumbnail),
                    contentDescription = "News Image", // later todo
                    modifier = Modifier
                        .fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                val iconRes = when {
                    article.url.contains("/video/") -> R.drawable.video_library_24px
                    article.url.contains("/photo/") -> R.drawable.photo_library_24px
                    else -> null
                }

                iconRes?.let {
                    Icon(
                        painter = painterResource(id = it),
                        contentDescription = if (article.url.contains("/video/")) "Video" else "Photo",
                        tint = PaloRed,
                        modifier = Modifier
                            .size(24.dp)
                            .align(Alignment.Center)
                    )
                }

            }
        }

            Text(
                text = article.date,
                modifier = Modifier.padding(start = 20.dp, bottom = 10.dp),
                fontWeight = FontWeight.Normal,
                fontSize = 13.sp,
                color = Color.Gray,
                fontFamily = ShurjoFamily,
            )

        HorizontalDivider(
            modifier = Modifier.padding(start = 20.dp, end = 20.dp),
            color = Color.Gray, thickness = 0.2.dp
        )
    }
}