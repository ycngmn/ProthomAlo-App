package com.ycngmn.prothomalo.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import com.ycngmn.prothomalo.NewsViewModel
import com.ycngmn.prothomalo.scraper.NewsContainer
import com.ycngmn.prothomalo.scraper.ProthomAlo
import com.ycngmn.prothomalo.scraper.ShurjoFamily
import com.ycngmn.prothomalo.ui.animation.LoadingAnimation
import com.ycngmn.prothomalo.ui.components.ArticleCard_V1
import com.ycngmn.prothomalo.ui.theme.PaloBlue
import com.ycngmn.prothomalo.utils.YouTubeVideo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun NewsLecture(navController: NavController, urlsVM: NewsViewModel, startIndex: Int = 0) {

    BackHandler {
        if (!navController.popBackStack(route = "topic/{topicKey}", inclusive = false))
            navController.popBackStack("home", inclusive = false)
    }

    val urls = urlsVM.newsUrls
    val pagerState = rememberPagerState(initialPage = startIndex, pageCount = { urls.size } )
    val coroutineScope = rememberCoroutineScope()
    val palo = ProthomAlo()

    val newsCache = remember { mutableStateMapOf<String, NewsContainer?>() }

    HorizontalPager(state = pagerState) { pageIndex ->

        var news by remember { mutableStateOf<NewsContainer?>(null) }

        if (!newsCache.containsKey(urls[pageIndex])) {
            LaunchedEffect(urls[pageIndex]) {
                coroutineScope.launch(Dispatchers.IO) {
                    val result = palo.getNews(urls[pageIndex])
                    news = result
                    newsCache[urls[pageIndex]] = result
                    if (urls.size > 10 && pageIndex > urls.size - 2 ) {
                         val newUrls = try {palo.getArticle(section = urlsVM.getSection(), offset = urls.size, limit = 12).map { it.url } }
                            catch (_ : Exception) { emptyList() }
                        urlsVM.updateUrls(
                            urls + newUrls
                        )
                    }
                }
            }
        } else {
            news = newsCache[urls[pageIndex]]
        }

        Column(
            Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
        ) {
            if (news == null)
                LoadingAnimation()

            else {
                NewsHead(news!!) {
                    if (!navController.popBackStack(route = "topic/$it", inclusive = false))
                        navController.navigate("topic/$it")

                }
                news!!.body.forEach {

                    if (it is String) {

                        if (it.contains("https://www.youtube.com/embed/")) {
                            if (pagerState.currentPage == pageIndex) {
                                YouTubeVideo(it)
                                Spacer(modifier = Modifier.padding(bottom = 16.dp))
                            }
                        }

                        else {
                            Text(
                                AnnotatedString.fromHtml(
                                    it.replace("</p><p>", "<br><br>")
                                        .replace(Regex("^<p>|</p>$"), "") + "<br>"
                                ),
                                modifier = Modifier.padding(horizontal = 16.dp),
                                fontFamily = ShurjoFamily,
                                fontWeight = FontWeight.Normal,
                                fontSize = 18.sp,
                                textAlign = TextAlign.Start,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    }
                    else if (it is Pair<*, *> && it.first.toString().isNotEmpty() && !it.first.toString().contains(".avif")) {
                            SubcomposeAsyncImage(
                                model = it.first.toString(),
                                contentDescription = "News Image",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .defaultMinSize(minHeight = 250.dp)
                                    .fillMaxWidth()
                                    .padding(top = 20.dp),
                                loading = { LoadingAnimation() },
                                onError = {
                                    // Android bug as understood at : https://github.com/coil-kt/coil/issues/1295
                                    // do nothing
                                }
                            )
                            if (it.second.toString() != "null") {
                                Text(
                                    text = it.second.toString(),
                                    Modifier.padding(16.dp, 16.dp, 16.dp, 10.dp),
                                    fontFamily = ShurjoFamily,
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 15.sp,
                                    color = Color.Gray,
                                    textAlign = TextAlign.Start
                                )
                            } else Spacer(modifier = Modifier.padding(bottom = 16.dp))

                    }
                }

                if (news!!.readAlso.isNotEmpty()) {
                    Card(
                        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                        modifier = Modifier.padding(16.dp).background(color = MaterialTheme.colorScheme.background),
                        colors = CardDefaults.cardColors(containerColor = Color.Unspecified)

                    ) {
                        Spacer(modifier = Modifier.padding(bottom = 20.dp))
                        val topicKey = news!!.readAlsoText
                        Text(
                            text = AnnotatedString.fromHtml("<u>$topicKey</u> নিয়ে আরও পড়ুন"),
                            modifier = Modifier.padding(16.dp, 7.dp, 16.dp, 20.dp)
                                .clickable {
                                    //
                                    if (!navController.popBackStack(route = "topic/$topicKey@$topicKey", inclusive = false))
                                        navController.navigate("topic/$topicKey@$topicKey")
                                           },
                            fontFamily = ShurjoFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        news!!.readAlso.forEachIndexed { index, it ->
                            ArticleCard_V1(it) {
                                urlsVM.updateUrls(news!!.readAlso.map { article -> article.url })
                                navController.navigate("news/$index")
                            }
                        }
                    }
                }
            }
        }
    }
}



@OptIn(ExperimentalLayoutApi::class)
@Composable
fun NewsHead(
    news: NewsContainer,
    onTopicClick: (String) -> Unit
) {
    Column (Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {

            Text(
                text = news.section,
                Modifier.padding(start = 16.dp, top = 10.dp, bottom = 10.dp).drawBehind {
                    val height = size.height
                    drawLine(
                        color = PaloBlue,
                        start = Offset(0f, height+10),
                        end = Offset(size.width, height+10),
                        strokeWidth = 4f
                    )
                }.clickable { onTopicClick(news.sectionSlug + "@" + news.section) },
                fontFamily = ShurjoFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp,
                color = PaloBlue,
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


        FlowRow (modifier = Modifier.padding(vertical = 10.dp)) {

            if (news.author != "null") {
                val authorText = news.author!!

                if (news.authorLocation != "null" && news.authorLocation.isNotEmpty())
                    authorText.plus(", $news.authorLocation")

                Text(
                    text = authorText,
                    Modifier.padding(start = 16.dp),
                    fontFamily = ShurjoFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 17.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = news.date,
                Modifier.padding(horizontal = 16.dp),
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

