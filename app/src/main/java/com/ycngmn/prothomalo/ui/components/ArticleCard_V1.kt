package com.ycngmn.prothomalo.ui.components

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.ycngmn.prothomalo.scraper.ArticleContainer
import com.ycngmn.prothomalo.scraper.ShurjoFamily


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

@Composable
fun ArticleCard_V1(
    article: ArticleContainer,
    navController: NavController = rememberNavController()
) {
    Column(modifier = Modifier.background(MaterialTheme.colorScheme.background)
        .clickable { navController.navigate("news/${Uri.encode(article.url)}") }) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Text(
                text = titleBuilder(article.subHead, article.title, Color(0xFFD60000), MaterialTheme.colorScheme.onBackground),
                modifier = Modifier.padding(start = 20.dp, top = 20.dp).weight(0.8f),
                fontFamily = ShurjoFamily,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )

            Image(
                painter = rememberAsyncImagePainter(article.thumbnail),
                contentDescription = "News Image", // later todo
                modifier = Modifier
                    .width(150.dp)
                    .height(100.dp)
                    .padding(start = 10.dp, top = 20.dp, end = 20.dp),
                contentScale = ContentScale.Crop
            )
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