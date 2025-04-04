package com.ycngmn.prothomalo.prothomalo

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.ycngmn.prothomalo.ui.screens.search.SearchViewModel

class PaloVM (
    private var section: String,
    var isTopic: Boolean = false,
    var searchVM: SearchViewModel? = null,
    val isSearch: Boolean = false
) : ViewModel() {


    var limit = 50

    var isLimitReached = mutableStateOf(false)
    var isSearchResEmpty = mutableStateOf(false)


    private var _offset = mutableIntStateOf(0)
    var offset: State<Int> = _offset

    private val _articles = mutableStateOf<List<ArticleContainer>>(emptyList())
    val articles: State<List<ArticleContainer>> = _articles

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