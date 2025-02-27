package com.ycngmn.prothomalo.scraper

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class ArticlesViewModel (key : String) : ViewModel() {

    var limit = 15
    private var section = ""

    private var _offset = mutableIntStateOf(0)
    var offset: State<Int> = _offset

    private val _articles = mutableStateOf<List<ArticleContainer>>(emptyList())
    val articles: State<List<ArticleContainer>> = _articles


    init {
        this.section = key
    }

    fun getSection() : String {
        return section
    }

    fun setOffset(newOffset : Int) {
        _offset.intValue = newOffset
    }

    fun setArticles(articles : List<ArticleContainer>) {
        _articles.value = articles
    }
}