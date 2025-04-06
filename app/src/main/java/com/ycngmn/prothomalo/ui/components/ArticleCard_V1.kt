package com.ycngmn.prothomalo.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.ycngmn.prothomalo.R
import com.ycngmn.prothomalo.prothomalo.ArticleContainer
import com.ycngmn.prothomalo.ui.components.helpers.titleBuilder
import com.ycngmn.prothomalo.ui.theme.PaloRed
import com.ycngmn.prothomalo.ui.assets.AppFont

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ArticleCard_V1(
    article: ArticleContainer,
    clickAction: () -> Unit,
) {
    val context = LocalContext.current

    Column(modifier = Modifier.background(MaterialTheme.colorScheme.background)
        .clickable { clickAction() }
    ) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {

            Text(
                text = titleBuilder(
                    article.subHead,
                    article.title, PaloRed,
                    MaterialTheme.colorScheme.onBackground),
                modifier = Modifier.padding(start = 20.dp, top = 20.dp).weight(0.8f),
                style = AppFont.titleTS
            )

            Box (modifier = Modifier.width(150.dp)
                .height(100.dp)
                .padding(start = 10.dp, top = 20.dp, end = 20.dp)) {

                SubcomposeAsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(article.thumbnail)
                        .crossfade(true).build(),
                    contentDescription = "News Image", // later todo
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                    error = { Image(painterResource(R.drawable.img), null) }
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
                style = AppFont.dateTS
            )

        HorizontalDivider(
            modifier = Modifier.padding(start = 20.dp, end = 20.dp),
            color = Color.Gray, thickness = 0.2.dp
        )
    }
}