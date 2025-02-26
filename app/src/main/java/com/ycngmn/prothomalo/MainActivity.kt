package com.ycngmn.prothomalo

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.compose.rememberAsyncImagePainter
import com.ycngmn.prothomalo.scraper.ArticleContainer
import com.ycngmn.prothomalo.scraper.ProthomAlo
import com.ycngmn.prothomalo.screens.NewsLecture
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            NavHost(navController = navController, startDestination = "home") {
                composable("home") { HomePage(navController) }
                composable("news/{url}",
                    arguments = listOf(navArgument("url") { type = NavType.StringType }),
                    enterTransition = { fadeIn(animationSpec = tween(300)) },
                    exitTransition = { slideOutHorizontally(animationSpec = tween(300), targetOffsetX = { -it }) }

                ) { backStackEntry ->
                    val url = backStackEntry.arguments?.getString("url") ?: ""
                    NewsLecture(Uri.encode(url))
                }
            }

        }
    }
}

@Composable
fun LoadingAnimation(size: Int = 100) {

    val scale = remember { Animatable(1f) }

    LaunchedEffect(Unit) {
        while (true) {
            scale.animateTo(
                0.8f,
                animationSpec = tween(600, easing = FastOutSlowInEasing)
            )
            scale.animateTo(
                1f,
                animationSpec = tween(600, easing = FastOutSlowInEasing)
            )
        }
    }

    Box (
       modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
       Image(
           painter = painterResource(id = R.drawable.pa_icon),
           contentDescription = "Loading logo",
           modifier = Modifier.scale(scale.value).size(size.dp)
       )
    }
}


val ShurjoFamily = FontFamily(
    Font(R.font.shurjo_regular, FontWeight.Normal),
    Font(R.font.shurjo_bold, FontWeight.Bold)
)

val prothomAlo = ProthomAlo()
class ArticlesViewModel (key : String) : ViewModel() {

    private var limit = 15
    private var section = ""

    private var _offset = mutableIntStateOf(0)
    var offset: State<Int> = _offset

    private val _articles = mutableStateOf<List<ArticleContainer>>(emptyList())
    val articles: State<List<ArticleContainer>> = _articles


    init {
        this.section = key
        this.loadArticles()
    }


    private suspend fun fetchArticlesFromNetwork(): List<ArticleContainer> {

        return withContext(Dispatchers.IO) { // Runs in background thread
             prothomAlo.getArticle(
                "https://www.prothomalo.com/api/v1/collections/$section?offset=${_offset.intValue}&limit=$limit"
            )
        }
    }

    private fun loadArticles() {

        viewModelScope.launch {
            _articles.value += fetchArticlesFromNetwork()
        }
    }

    fun loadMoreArticles() {
        _offset.value += limit
        loadArticles()
    }
}



@Composable
fun HomePage(navController: NavController ) {

    val articleVMs = remember {
        mutableMapOf<String, ArticlesViewModel>()
    }
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { ProthomAlo().articleSections.size })

    Scaffold (
        topBar = {
            Surface(shadowElevation = 3.dp) {
                TopBar(pagerState)
            }
        },
        bottomBar = { BottomBar() }
    )

    {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .background(Color.White)
        ) {


            HorizontalPager(state = pagerState) { page ->

                val key = ProthomAlo().articleSections.keys.toList()[page]


                val articleVM = articleVMs.getOrPut(key) {
                    @Suppress("UNCHECKED_CAST")
                    viewModel( key = key,
                        factory = object : ViewModelProvider.Factory {
                        override fun <T : ViewModel> create(modelClass: Class<T>): T {
                            return ArticlesViewModel(key) as T
                        }
                    })
                }

                SimpleNewsCard(articleVM, navController)
            }


        }


    }
}



@Composable
fun SimpleNewsCard(
    articlesVM : ArticlesViewModel,
    navController : NavController
) {

    if (articlesVM.articles.value.isEmpty()) {
        LoadingAnimation()
        return
    }

    val listState = rememberLazyListState()

    val isLoadMore by remember(articlesVM.offset) {
        derivedStateOf {
            val lastVisibleItem = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            lastVisibleItem >= articlesVM.offset.value - 5 && articlesVM.articles.value.isNotEmpty()
        }
    }

    LaunchedEffect(isLoadMore) {
        if (isLoadMore)
            articlesVM.loadMoreArticles()
    }

    Column {

        LazyColumn(state = listState) {

            items(articlesVM.articles.value) { article ->
                ArticleCard(article, navController)
            }

            item {
                LinearProgressIndicator(
                    modifier = Modifier
                        .padding(20.dp).fillMaxWidth(),
                    trackColor = Color.White,
                )
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun Ann() {
    LinearProgressIndicator(
        modifier = Modifier
            .padding(20.dp).fillMaxWidth(),
        color = Color.hsl(0f, 1f, 0.42f),
        trackColor = Color.Gray
    )
}


@Composable
fun ArticleCard(
    article: ArticleContainer,
    navController: NavController = rememberNavController()
) {
    Column(modifier = Modifier.clickable { navController.navigate("news/${Uri.encode(article.url)}") }) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Text(
                text = buildAnnotatedString {
                    if (article.subHead != "null") {
                        withStyle(
                            style = SpanStyle(
                                color = Color.hsl(0f, 1f, 0.42f),
                                fontFamily = ShurjoFamily,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        ) { append(article.subHead) }

                        withStyle(SpanStyle(color = Color.Gray, fontSize = 12.sp)) {
                            append(" ● ")
                        }
                    }
                    withStyle(
                        SpanStyle(
                            color = Color.Black,
                            fontFamily = ShurjoFamily,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                        )
                    ) { append(article.title) }

                },
                modifier = Modifier.padding(start = 20.dp, top = 20.dp).weight(0.8f)
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
            fontSize = 12.sp,
            color = Color.Gray,
            fontFamily = ShurjoFamily,
        )

        HorizontalDivider(
            modifier = Modifier.padding(start = 20.dp, end = 20.dp),
            color = Color.Gray, thickness = 0.2.dp
        )

    }
}




@Composable
fun TopBar(pageState: PagerState) {

    val coroutineScope = rememberCoroutineScope()

    Column (
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White),
        verticalArrangement = Arrangement.SpaceBetween
        )
       {
            Row (
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            )
            {
                Image(
                    painter = painterResource(R.drawable.main_logo),
                    contentDescription = "ProthomAlo_Logo_White",
                )

                Spacer(modifier = Modifier.weight(1f))

                Icon(
                    painterResource(R.drawable.profile_setting_icon),
                    contentDescription = "Account_and_Setting_logo",
                    modifier = Modifier.size(35.dp)

                )

            }

           val articleSections = ProthomAlo().articleSections.keys.toList()

           ScrollableTabRow (
               edgePadding = 2.dp,
               containerColor = Color.White,
               selectedTabIndex = pageState.currentPage) {
               articleSections.forEachIndexed { index, value ->

                   Tab(
                       selected = pageState.currentPage == index,
                       onClick = {
                           coroutineScope.launch {
                               pageState.animateScrollToPage(index)
                           }
                       },
                       text = {
                           Text(
                               text = ProthomAlo().articleSections[value] ?: "",
                               color = Color.Black,
                               fontFamily = ShurjoFamily,
                               fontSize = 14.sp,
                               fontWeight = FontWeight.Normal,)
                       }
                   )

               }
           }

        }


}



data class BottomNavItem(
    val route: String,
    val icon: Int,
    val label: String
)


@Composable
fun BottomBar() {

    //val selectedItem = remember { mutableIntStateOf(0) }

    val sections = listOf(
        BottomNavItem("Home", R.drawable.pa_icon, "Home"),
        BottomNavItem("Explore", R.drawable.discover_icon, "Explore"),
        BottomNavItem("Bookmark", R.drawable.bookmark_icon, "Bookmark"),
        BottomNavItem("Menu", R.drawable.menu_icon, "Menu")
    )

    BottomAppBar(
        containerColor = Color.White,
        modifier = Modifier
            .shadow(20.dp)
            .height(65.dp)
    ) {

    Row (
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxWidth()
    )
    {
            for (section in sections) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .clickable(onClick = {})
                        .padding(8.dp)

                ) {
                    Icon(
                        modifier = Modifier.size(25.dp),
                        painter = painterResource(id = section.icon),
                        contentDescription = section.label)
                    Text(
                        modifier = Modifier.padding(top = 4.dp),
                        text = section.label, fontSize = 8.sp, color = Color.Gray,
                        fontFamily = ShurjoFamily, fontWeight = FontWeight.Bold)
                }
            }
        }
    }

}



