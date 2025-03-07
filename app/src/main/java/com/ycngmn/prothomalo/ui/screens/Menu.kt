package com.ycngmn.prothomalo.ui.screens

import android.annotation.SuppressLint
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import com.ycngmn.prothomalo.scraper.ShurjoFamily
import com.ycngmn.prothomalo.ui.screens.home.BottomBar

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun YouTubeVideo(videoId: String, width: Int = 400, height: Int = 300) {
    Box(
        modifier = Modifier
            .width(width.dp)
            .height(height.dp)
    ) {
        AndroidView(
            factory = { context ->
                WebView(context).apply {
                    layoutParams = android.view.ViewGroup.LayoutParams(
                        android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                        android.view.ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    settings.javaScriptEnabled = true
                    webChromeClient = WebChromeClient()
                    webViewClient = WebViewClient()
                    loadUrl("https://www.youtube.com/embed/$videoId")
                }
            },
            modifier = Modifier.fillMaxSize() // Ensures WebView stays inside Box
        )
    }
}

@Composable
fun MenuScreen(navController: NavHostController) {


    Scaffold(
        topBar = { MenuTopBar() },
        bottomBar = { BottomBar(navController) }
    ) {
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(it)
                .padding(16.dp),
        ) {

            Column(Modifier.height(200.dp)) {
                YouTubeVideo("3JW732GrMdg")
            }
            Text(
                "Hzislkgs"
            )


        }

    }

}


@Composable
fun MenuTopBar() {
    Surface(
        modifier = Modifier.background(MaterialTheme.colorScheme.background),
        shadowElevation = 4.dp
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Text(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(horizontal = 40.dp),
                text = "Menu",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = ShurjoFamily,
                color = MaterialTheme.colorScheme.onBackground,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}