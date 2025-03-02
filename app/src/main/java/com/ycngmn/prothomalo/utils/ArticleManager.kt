package com.ycngmn.prothomalo.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ycngmn.prothomalo.scraper.ArticleContainer
import com.ycngmn.prothomalo.scraper.ArticlesViewModel
import com.ycngmn.prothomalo.scraper.ProthomAlo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// Apparently,serves only the Homepage.

class ArticleEngine(
    private val viewModel: ArticlesViewModel,
    private val articleClass : ProthomAlo = ProthomAlo()
) : ViewModel() {

    private suspend fun fetchArticlesFromNetwork(): List<ArticleContainer> {

        return withContext(Dispatchers.IO) {
            if (!viewModel.isTopic)
                articleClass.getArticle(
                    viewModel.getSection(),viewModel.offset.value,viewModel.limit
                )
            else
                articleClass.getSeeMore(viewModel.getSection(), urlToSkip = "&@àml", // to detect see more, not the best of the methods but works
                    offset = viewModel.offset.value,limit = viewModel.limit)
        }
    }

    fun loadArticles() {
        viewModelScope.launch {
            viewModel.setArticles(viewModel.articles.value + fetchArticlesFromNetwork())
        }
    }

    fun loadMoreArticles() {
        viewModel.setOffset(viewModel.offset.value + viewModel.limit)
        loadArticles()
    }

    fun refreshArticles() {
        viewModel.setOffset(0)
        viewModel.setArticles(emptyList())
        loadArticles()
    }


}