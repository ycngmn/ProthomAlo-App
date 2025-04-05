package com.ycngmn.prothomalo.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.ycngmn.prothomalo.R
import com.ycngmn.prothomalo.prothomalo.ArticleContainer
import com.ycngmn.prothomalo.prothomalo.ShurjoFamily
import com.ycngmn.prothomalo.ui.theme.PaloOrange


@Composable
fun ArticleCard_V2(
    article: ArticleContainer,
    clickAction: () -> Unit,
) {

    val context = LocalContext.current

    Box (modifier = Modifier.background(MaterialTheme.colorScheme.background).fillMaxWidth()
        .clickable { clickAction() }) {

        SubcomposeAsyncImage(
            model = ImageRequest.Builder(context)
                .data(article.thumbnail)
                .crossfade(true).build(),
            contentDescription = "News Image", // later todo
            modifier = Modifier.defaultMinSize(minHeight = 250.dp).fillMaxWidth(),
            contentScale = ContentScale.Crop,
            error = { Image(painterResource(R.drawable.img), null) }
        )

        Box(
            Modifier.matchParentSize()
                .padding(0.dp)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Black.copy(alpha = 1f)
                        )
                    )
                )
        ) {

            Column (Modifier.align(Alignment.BottomStart).padding(16.dp)) {
                Text(
                    modifier = Modifier,
                    text = titleBuilder(article.subHead, article.title,
                        PaloOrange, Color.White ),
                    fontFamily = ShurjoFamily,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = article.date,
                    fontFamily = ShurjoFamily,
                    fontSize = 16.sp,
                    color = Color.Gray
                )
            }
        }


    }
}