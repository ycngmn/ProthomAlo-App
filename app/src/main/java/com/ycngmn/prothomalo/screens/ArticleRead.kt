package com.ycngmn.prothomalo.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.ycngmn.prothomalo.LoadingAnimation
import com.ycngmn.prothomalo.ShurjoFamily
import com.ycngmn.prothomalo.scraper.NewsContainer
import com.ycngmn.prothomalo.scraper.ProthomAlo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@Composable
fun NewsLecture(url:String) {

    val palo = ProthomAlo()
    var news by remember { mutableStateOf<NewsContainer?>(null) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        coroutineScope.launch(Dispatchers.IO) {
            val result = palo.getNews(url)
            withContext(Dispatchers.Main) {  // Switch back to UI thread
                news = result
            }
        }
    }



    Column (
        Modifier.fillMaxSize()
        .background(Color.White)
        .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (news == null) {
            LoadingAnimation()
        } else {

            NewsHead(
                section = news!!.section,
                title = news!!.headline,
                summary = news!!.summary,
                author = news!!.author,
                date = news!!.date
            )

            news!!.body.forEach {
                if (it is String) {

                    Text(
                        AnnotatedString.fromHtml(htmlString = it
                            .replace("</p><p>", "<br><br>")
                            .replace(Regex("^<p>|</p>\$"), "") + "<br>") ,
                        modifier = Modifier.padding(horizontal = 16.dp),
                        fontFamily = ShurjoFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 18.sp,
                        textAlign = TextAlign.Start)

                }

                else if (it is Pair<*, *> && it.first.toString().isNotEmpty()) {

                    SubcomposeAsyncImage(
                        model = it.first.toString(),
                        contentDescription = "News Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 20.dp),
                        loading = {
                            LoadingAnimation()
                        }
                    )
                    Text(text = it.second.toString(),
                        Modifier.padding(16.dp,16.dp,16.dp, 10.dp),
                        fontFamily = ShurjoFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 12.sp,
                        color = Color.Gray)

                }
            }
        }
    }
}

@Composable
fun NewsHead(
    section: String,
    title: String,
    summary: String?,
    author: String?,
    date: String,
) {
    Column (Modifier
        .fillMaxSize()
        .background(Color.White)) {

            Text(
                text = section,
                Modifier.padding(start = 16.dp, top = 10.dp),
                fontFamily = ShurjoFamily,
                fontWeight = FontWeight.Thin,
                fontSize = 15.sp,
            )

            Text(
                text = title,
                Modifier.padding(16.dp, 7.dp, 16.dp, 10.dp),
                fontFamily = ShurjoFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp
            )

        if (summary!="null")
            Text(
                text = summary!!,
                Modifier.padding(horizontal = 16.dp),
                fontFamily = ShurjoFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 15.sp
            )
        if (author != "null")
            Text(
                text = author!!,
                Modifier.padding(top = 10.dp, start = 16.dp),
                fontFamily = ShurjoFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp
            )

            Text(
                text = date,
                Modifier.padding(start = 16.dp),
                fontFamily = ShurjoFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp
            )

            HorizontalDivider(modifier = Modifier
                .padding(16.dp, 10.dp, 10.dp)
                .width(35.dp))
        }
}

