package com.ycngmn.prothomalo.ui.screens

import androidx.activity.compose.BackHandler
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import com.ycngmn.prothomalo.scraper.NewsContainer
import com.ycngmn.prothomalo.scraper.ProthomAlo
import com.ycngmn.prothomalo.scraper.ShurjoFamily
import com.ycngmn.prothomalo.ui.animation.LoadingAnimation
import com.ycngmn.prothomalo.ui.components.ArticleCard_V1
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

val paloBlue = Color.hsl(211f,0.94f,0.44f)

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
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,

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
                        textAlign = TextAlign.Start,
                        color = MaterialTheme.colorScheme.onBackground
                    )
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
                            fontSize = 15.sp,
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
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
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
            }
        }

    }
}


@Composable
fun NewsHead(
    news: NewsContainer
) {
    Column (Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {

            Text(
                text = news.section,
                Modifier.padding(start = 16.dp, top = 10.dp, bottom = 10.dp).drawBehind {
                    val height = size.height
                    drawLine(
                        color = paloBlue,
                        start = Offset(0f, height+10),
                        end = Offset(size.width, height+10),
                        strokeWidth = 4f
                    )
                },
                fontFamily = ShurjoFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp,
                color = paloBlue,
            )

            Text(
                text = news.headline,
                Modifier.padding(16.dp, 7.dp, 25.dp, 10.dp),
                fontFamily = ShurjoFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 25f.sp,
                color = MaterialTheme.colorScheme.onBackground
            )

        if (news.summary!="null")
            Text(
                text = news.summary!!,
                Modifier.padding(horizontal = 16.dp),
                fontFamily = ShurjoFamily,
                fontWeight = FontWeight.Normal,
                color = Color.Gray,
                fontSize = 18.sp
            )


        Row (modifier = Modifier.padding(vertical = 10.dp)) {

            if (news.author != "null")
                Text(
                    text = news.author!!,
                    Modifier.padding(start = 16.dp),
                    fontFamily = ShurjoFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 17.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )

            if (news.authorLocation != "null" && news.authorLocation.isNotEmpty()) {
                Text(
                    text = ", " + news.authorLocation,
                    fontFamily = ShurjoFamily,
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 17.sp
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = news.date,
                Modifier.padding(end = 16.dp),
                fontFamily = ShurjoFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 17.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

    HorizontalDivider(modifier = Modifier
        .padding(16.dp, 5.dp, 10.dp)
        .width(35.dp),
        thickness = 3.dp,
    )

    }
}

