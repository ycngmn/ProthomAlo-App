package com.ycngmn.prothomalo.prothomalo

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class PaloVM (
    key : String,
    isTopic: Boolean = false) : ViewModel() {


    var isTopic: Boolean

    var limit = 50
    private var section = ""
    var isLimitReached = mutableStateOf(false)
    var isSearchResEmpty = mutableStateOf(false)


    private var _offset = mutableIntStateOf(0)
    var offset: State<Int> = _offset

    private val _articles = mutableStateOf<List<ArticleContainer>>(emptyList())
    val articles: State<List<ArticleContainer>> = _articles

    init {
        this.section = key
        this.isTopic = isTopic
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