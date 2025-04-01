package com.ycngmn.prothomalo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.ycngmn.prothomalo.prothomalo.NewsContainer


class NewsViewModel : ViewModel() {
    private var section = ""
    private var newsUrls by mutableStateOf<List<String>>(emptyList())
    var newsCache by mutableStateOf<Map<String, NewsContainer?>>(emptyMap())

    fun updateNewsCache(url: String, news: NewsContainer?) {
        newsCache = newsCache.toMutableMap().apply { this[url] = news }
    }

    fun updateUrls(newUrls: List<String>) {
        newsUrls = newUrls
    }
    fun setSection(newSection: String) {
        section = newSection
    }
    fun getSection(): String {
        return section
    }
    fun getUrls(): List<String> {
        return newsUrls
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainNavGraph()
        }
    }
}




