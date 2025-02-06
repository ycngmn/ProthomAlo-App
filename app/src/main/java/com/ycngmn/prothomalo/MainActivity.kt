package com.ycngmn.prothomalo

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsApp()
        }
    }
}

data class NewsArticle(
    val title: String,
    val summary: String,
    val imageUrl: String,
    val url: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsApp() {
    val newsArticles = listOf(
        NewsArticle(
            title = "Jetpack Compose is Awesome!",
            summary = "Google's new UI toolkit makes Android development easier and faster.",
            imageUrl = "https://cdn6.aptoide.com/imgs/1/3/8/1380470209c8058cd0fedc4092d5c8fa_icon.png",
            url = "https://developer.android.com/jetpack/compose"
        ),
        NewsArticle(
            title = "Kotlin 1.7 Released!",
            summary = "The latest Kotlin update brings new features and improvements.",
            imageUrl = "https://cdn6.aptoide.com/imgs/1/3/8/1380470209c8058cd0fedc4092d5c8fa_icon.png",
            url = "https://kotlinlang.org"
        ),
        NewsArticle(
            title = "Android 14 New Features!",
            summary = "Google has unveiled exciting new features in Android 14.",
            imageUrl = "https://cdn6.aptoide.com/imgs/1/3/8/1380470209c8058cd0fedc4092d5c8fa_icon.png",
            url = "https://developer.android.com/about/versions/14/"
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("News App") })
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .padding(8.dp)
        ) {
            items(newsArticles.size) { index ->
                NewsCard(newsArticles[0])
            }
        }
    }
}

@Composable
fun NewsCard(article: NewsArticle) {
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(article.url))
                context.startActivity(intent)
            },
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Image(
                painter = rememberAsyncImagePainter(article.imageUrl),
                contentDescription = "News Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = article.title, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = article.summary, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewNewsApp() {
    NewsApp()
}