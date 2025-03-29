package com.ycngmn.prothomalo.ui.screens.article

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
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
import com.ycngmn.prothomalo.scraper.ShurjoFamily
import com.ycngmn.prothomalo.ui.animation.LoadingAnimation
import com.ycngmn.prothomalo.ui.components.ArticleCard_V1
import com.ycngmn.prothomalo.ui.theme.PaloBlue
import com.ycngmn.prothomalo.utils.PaloGlobal
import com.ycngmn.prothomalo.utils.ThemeViewModel
import com.ycngmn.prothomalo.utils.YouTubeVideo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun NewsLecture(
    navController: NavController,
    urlsVM: NewsViewModel,
    startIndex: Int = 0,
    themeViewModel: ThemeViewModel) {

    BackHandler { // these if statements execute stuffs
        if (!navController.popBackStack(route = "topic/{topicKey}", inclusive = false)) {
            if (!navController.popBackStack(route = "search", inclusive = false))
                navController.popBackStack("home", inclusive = false)
        }
    }

    val urls = urlsVM.newsUrls
    val pagerState = rememberPagerState(initialPage = startIndex, pageCount = { urls.size })
    val coroutineScope = rememberCoroutineScope()
    val palo = PaloGlobal.getPalo()
    val newsCache = remember { mutableStateMapOf<String, NewsContainer?>() }

    HorizontalPager(state = pagerState) { pageIndex ->

        var news by remember { mutableStateOf<NewsContainer?>(null) }
        val url = urls[pageIndex]

        LaunchedEffect(url) {
            if (!newsCache.containsKey(url)) {
                coroutineScope.launch(Dispatchers.IO) {
                    palo.getNews(url).let { result ->
                        news = result
                        newsCache[url] = result
                    }
                    // Load more articles if near the end
                    if (urls.size > 10 && pageIndex > urls.size - 2) {
                        val newUrls = runCatching {
                            palo.getArticle(
                                section = urlsVM.getSection(),
                                offset = urls.size,
                                limit = 12
                            ).map { it.url }
                        }.getOrElse { emptyList() }
                        urlsVM.updateUrls(urls + newUrls)
                    }
                }
            }
            else news = newsCache[url]
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

                    if (it.second.contains("https://www.youtube.com/embed/")) {
                        if (pagerState.currentPage == pageIndex) {
                            YouTubeVideo(it.second)
                            Spacer(modifier = Modifier.padding(bottom = 16.dp))
                        }
                    }

                    else if (it.first == "text") {
                        Text(
                            AnnotatedString.fromHtml(
                                it.second.replace("</p><p>", "<br><br>")
                                    .replace(Regex("^<p>|</p>$"), "") + "<br>",
                                linkStyles = TextLinkStyles(
                                    SpanStyle(color = PaloBlue, fontWeight = FontWeight.Bold))),
                            modifier = Modifier.padding(horizontal = 16.dp),
                            fontFamily = ShurjoFamily,
                            fontWeight = FontWeight.Normal,
                            fontSize = 18.sp,
                            textAlign = TextAlign.Start,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                    else if (it.first == "image" && !it.second.contains(".avif") && it.second.isNotEmpty()) {
                            SubcomposeAsyncImage(
                                model = it.second,
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
                    }
                    else if (it.second.toString() != "null" && it.second.toString().isNotEmpty()) {
                        Text(
                            text = it.second.toString(),
                            Modifier.padding(16.dp, 16.dp, 16.dp, 10.dp),
                            fontFamily = ShurjoFamily,
                            fontWeight = FontWeight.Normal,
                            fontSize = 15.sp,
                            color = Color.Gray,
                            textAlign = TextAlign.Start
                        )
                    }

                }

                if (news!!.readAlso.isNotEmpty() && themeViewModel.isSeeMoreEnabled.value) {
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


