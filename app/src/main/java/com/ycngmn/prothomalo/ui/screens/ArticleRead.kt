package com.ycngmn.prothomalo.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import com.ycngmn.prothomalo.R
import com.ycngmn.prothomalo.scraper.NewsContainer
import com.ycngmn.prothomalo.scraper.ProthomAlo
import com.ycngmn.prothomalo.scraper.ShurjoFamily
import com.ycngmn.prothomalo.ui.animation.LoadingAnimation
import com.ycngmn.prothomalo.ui.components.ArticleCard_V1
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@Composable
fun NewsLecture(navController: NavController,url:String) {

    BackHandler {
        navController.popBackStack("home", inclusive = false)
    }
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
        Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (news == null) {
            LoadingAnimation()
        } else {

            NewsHead(news!!)

            news!!.body.forEach {
                if (it is String) {

                    Text( // TODO : format manually the html to match palo styling.
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
                        modifier = Modifier.defaultMinSize(minHeight = 300.dp)
                            .fillMaxWidth()
                            .padding(top = 20.dp),
                        loading = {
                            LoadingAnimation()
                        }
                    )
                    if (it.second.toString() != "null") {
                        Text(text = it.second.toString(),
                            Modifier.padding(16.dp,16.dp,16.dp, 10.dp),
                            fontFamily = ShurjoFamily,
                            fontWeight = FontWeight.Normal,
                            fontSize = 13.sp,
                            color = Color.Gray,
                            textAlign = TextAlign.Start)
                    }
                    else
                        Spacer(modifier = Modifier.padding(bottom = 16.dp))

                }
            }

            if (news!!.readAlso.isNotEmpty()) {

                Card(
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.padding(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Spacer(modifier = Modifier.padding(bottom = 20.dp))
                    Text(
                        text = AnnotatedString.fromHtml(htmlString = news!!.readAlsoText),
                        Modifier.padding(16.dp, 7.dp, 16.dp, 20.dp),
                        fontFamily = ShurjoFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                    news!!.readAlso.forEach { ArticleCard_V1(it,navController) }
                }

                Image(
                    modifier = Modifier.scale(0.4f),
                    painter = painterResource(R.drawable.palo_footer),
                    contentDescription = "ProthomAlo footer",
                )
            }

        }

    }
}

@Composable
fun NewsHead(
    news: NewsContainer
) {
    Column (Modifier
        .fillMaxSize()
        .background(Color.White)) {

            Text(
                text = news.section,
                Modifier.padding(start = 16.dp, top = 10.dp, bottom = 10.dp).drawBehind {
                    val height = size.height
                    drawLine(
                        color = Color.hsl(211f,0.94f,0.44f),
                        start = Offset(0f, height+10),
                        end = Offset(size.width, height+10),
                        strokeWidth = 4f
                    )
                },
                fontFamily = ShurjoFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp,
                color = Color.hsl(211f,0.94f,0.44f),
            )

            Text(
                text = news.headline,
                Modifier.padding(16.dp, 7.dp, 25.dp, 10.dp),
                fontFamily = ShurjoFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp
            )

        if (news.summary!="null")
            Text(
                text = news.summary!!,
                Modifier.padding(horizontal = 16.dp),
                fontFamily = ShurjoFamily,
                fontWeight = FontWeight.Normal,
                color = Color.Gray,
                fontSize = 16.sp
            )
        if (news.author != "null")

            Row {

                Text(
                    text = news.author!!,
                    Modifier.padding(top = 10.dp, start = 16.dp),
                    fontFamily = ShurjoFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )

                if (news.authorLocation != "null") {
                    Text(
                        text = news.authorLocation,
                        Modifier.padding(top = 10.dp, start = 6.dp),
                        fontFamily = ShurjoFamily,
                        fontWeight = FontWeight.Normal,
                        color = Color.Black,
                        fontSize = 13.sp
                    )
                }
            }

        Text(
            text = news.date,
            Modifier.padding(start = 16.dp),
            fontFamily = ShurjoFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 15.sp
        )

        HorizontalDivider(modifier = Modifier
            .padding(16.dp, 10.dp, 10.dp)
            .width(35.dp),
            thickness = 3.dp,
        )

        }
}

