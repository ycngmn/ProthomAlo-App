package com.ycngmn.prothomalo.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ycngmn.prothomalo.prothomalo.ArticleContainer
import com.ycngmn.prothomalo.prothomalo.ArticlesViewModel
import com.ycngmn.prothomalo.prothomalo.ProthomAlo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ArticleEngine(
    private val viewModel: ArticlesViewModel,
    private val articleClass : ProthomAlo
) : ViewModel() {

    private suspend fun fetchArticlesFromNetwork(): List<ArticleContainer> {

        return withContext(Dispatchers.IO) {

            if (viewModel.isSearch) {
                articleClass.search(
                    query =  viewModel.searchVM.searchText,
                    author = viewModel.searchVM.selectedAuthor,
                    sections = viewModel.searchVM.selectedSections,
                    types = viewModel.searchVM.selectedTypes,
                    offset = viewModel.offset.value,
                    limit = viewModel.limit,
                )
            }

            else if (!viewModel.isTopic) {
                try {
                    articleClass.getArticle(
                        viewModel.getSection(), viewModel.offset.value, viewModel.limit
                    )
                } catch (_ : Exception) {
                    viewModel.isLimitReached = true
                    viewModel.setArticles(viewModel.articles.value.dropLast(1))
                    emptyList()
                }
            }
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
        viewModel.nbArticles.intValue = -1
    }

}