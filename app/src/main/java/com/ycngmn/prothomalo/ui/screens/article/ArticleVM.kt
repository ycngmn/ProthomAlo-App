package com.ycngmn.prothomalo.ui.screens.article

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ycngmn.prothomalo.prothomalo.ArticleContainer
import com.ycngmn.prothomalo.prothomalo.PaloVM
import com.ycngmn.prothomalo.prothomalo.ProthomAlo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jsoup.HttpStatusException
import java.net.ConnectException

class ArticleEngine(
    private val viewModel: PaloVM,
    private val articleClass : ProthomAlo
) : ViewModel() {

    var isHttpError = mutableStateOf(false)

    private suspend fun fetchArticlesFromNetwork(): List<ArticleContainer> {

        return withContext(Dispatchers.IO) {

         if (!viewModel.isTopic) {
                try {
                    articleClass.getArticle(
                        viewModel.getSection(), viewModel.offset.value, viewModel.limit
                    )
                } catch (_ : HttpStatusException) {
                    viewModel.isLimitReached.value = true
                    viewModel.setArticles(viewModel.articles.value.dropLast(1))
                    emptyList()
                } catch (_ : ConnectException) {
                    isHttpError.value = true
                    emptyList()
                } catch (_ : Exception) {
                    isHttpError.value = true
                    emptyList()
                }
            }
            else {
                // to detect see more, not the best of the methods but works
                articleClass.getSeeMore(
                    viewModel.getSection(), urlToSkip = "&@àml",
                    offset = viewModel.offset.value, limit = viewModel.limit
                )
            }
        }
    }

    fun setSearchArticles(
        query: String, author: String,
        sections: List<String>, types: List<String>,
    ) {
         viewModelScope.launch {
            withContext(Dispatchers.IO) {
                viewModel.setArticles(
                    articleClass.search(
                        query = query,
                        author = author,
                        sections = sections,
                        types = types,
                        offset = viewModel.offset.value,
                        limit = viewModel.limit
                    )
                )
            }
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