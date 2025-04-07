package com.ycngmn.prothomalo.ui.screens.article

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.text.selection.DisableSelection
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.ycngmn.prothomalo.R
import com.ycngmn.prothomalo.prothomalo.PaloGlobal
import com.ycngmn.prothomalo.prothomalo.containers.NewsContainer
import com.ycngmn.prothomalo.ui.animation.LoadingAnimation
import com.ycngmn.prothomalo.ui.assets.ArticleFont
import com.ycngmn.prothomalo.ui.assets.Strings
import com.ycngmn.prothomalo.ui.assets.TextStyles
import com.ycngmn.prothomalo.ui.components.ArticleCard_V1
import com.ycngmn.prothomalo.ui.screens.bookmark.BookmarkDao
import com.ycngmn.prothomalo.ui.screens.error.ErrorConnection
import com.ycngmn.prothomalo.ui.screens.settings.SettingsVM
import com.ycngmn.prothomalo.ui.theme.PaloBlue
import com.ycngmn.prothomalo.utils.YouTubeVideo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsLecture(
    navController: NavController,
    urlsVM: NewsViewModel,
    startIndex: Int = 0,
    settingsVM: SettingsVM,
    bookmarkDao: BookmarkDao
) {

    BackHandler { // ! these if statements execute stuffs !

        while (navController.navigateUp() &&
            !navController.popBackStack(route = "topic/{topicKey}", inclusive = false)) {
            if (navController.currentDestination?.route != "news/{index}")
                break
        }

    }

    val urls = urlsVM.getUrls()
    val pagerState = rememberPagerState(initialPage = startIndex, pageCount = { urls.size })
    val coroutineScope = rememberCoroutineScope()
    val palo = PaloGlobal.getPalo()
    val newsCache = urlsVM.newsCache
    val context = LocalContext.current

    var isShowError by remember { mutableStateOf(false) }
    if (isShowError) {
        ErrorConnection { isShowError = false }
        return
    }

    HorizontalPager(state = pagerState) { pageIndex ->

        val url = urls[pageIndex]
        var news by remember { mutableStateOf<NewsContainer?>(newsCache[url]) }

        LaunchedEffect(url) {

            if (!newsCache.containsKey(url)) {
                coroutineScope.launch(Dispatchers.IO) {

                    try {
                        palo.getNews(url).let { result ->
                            news = result
                            urlsVM.updateNewsCache(url,result)
                        }
                    } catch (_: Exception) { isShowError = true }

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
                NewsHead(news!!, bookmarkDao, settingsVM) {
                    if (!navController.popBackStack(route = "topic/$it", inclusive = false))
                        navController.navigate("topic/$it")

                }
                news!!.body.forEach {

                    if (it.second.isEmpty() || it.second == "null")
                        return@forEach

                    when (it.first) {
                        "text" -> {
                            Text(
                                AnnotatedString.fromHtml(
                                    it.second.replace("</p><p>", "<br><br>")
                                        .replace(Regex("^<p>|</p>$"), "") + "<br>",
                                    linkStyles = TextLinkStyles(
                                        SpanStyle(color = PaloBlue, fontWeight = FontWeight.Bold)
                                    )
                                ),
                                modifier = Modifier.padding(horizontal = 16.dp),
                                color = MaterialTheme.colorScheme.onBackground,
                                fontSize = ArticleFont.articleBody,
                                style = TextStyles.defaultTS(),
                            )
                        }

                        "image" -> {
                            SubcomposeAsyncImage(
                                model = ImageRequest.Builder(context)
                                    .data(it.second)
                                    .crossfade(true).build(),
                                contentDescription = "News Image",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .defaultMinSize(minHeight = 250.dp)
                                    .fillMaxWidth()
                                    .padding(vertical = 10.dp),
                                loading = { LoadingAnimation() },
                                error = {
                                    // Android bug as understood at : https://github.com/coil-kt/coil/issues/1295
                                    Image(painterResource(R.drawable.placeholder), null)
                                }
                            )
                        }

                        "caption" -> {
                            DisableSelection {
                                Text(
                                    text = it.second.toString(),
                                    Modifier.padding(16.dp, 2.dp, 16.dp, 10.dp),
                                    fontSize = ArticleFont.articleCaption,
                                    style = TextStyles.defaultTS(),
                                    color = Color.Gray
                                )
                            }
                        }

                        "video" -> {
                            if (pagerState.currentPage == pageIndex) {
                                YouTubeVideo(it.second)
                                Spacer(modifier = Modifier.padding(bottom = 16.dp))
                            }
                        }

                    }
                }

                DisableSelection {

                    if (news!!.readAlso.isNotEmpty() && settingsVM.isSeeMoreEnabled.value) {
                        Card(
                            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                            modifier = Modifier.padding(16.dp)
                                .background(color = MaterialTheme.colorScheme.background),
                            colors = CardDefaults.cardColors(containerColor = Color.Unspecified)

                        ) {
                            Spacer(modifier = Modifier.padding(bottom = 20.dp))
                            val topicKey = news!!.readAlsoText
                            Text(
                                text = AnnotatedString.fromHtml(Strings.get("read_more_title", topicKey)),
                                modifier = Modifier.padding(16.dp, 7.dp, 16.dp, 20.dp)
                                    .clickable {
                                        if (!navController.popBackStack(
                                                route = "topic/$topicKey@$topicKey",
                                                inclusive = false
                                            )
                                        )
                                            navController.navigate("topic/$topicKey@$topicKey")
                                    },
                                fontSize = ArticleFont.articleSection,
                                style = TextStyles.defaultTS(),
                                fontWeight = FontWeight.Bold,
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
}


