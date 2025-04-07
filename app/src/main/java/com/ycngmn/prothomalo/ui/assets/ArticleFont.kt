package com.ycngmn.prothomalo.ui.assets

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.sp

object ArticleFont {

    private var currArticleSize = 0

    private const val MAX_INCREMENT = 5
    private const val MIN_INCREMENT = -5

    var articleTitle by mutableStateOf(25.sp)
    var articleSub by mutableStateOf(17.sp)
    var articleBody by mutableStateOf(18.sp)
    var articleCaption by mutableStateOf(15.sp)
    var articleSection by mutableStateOf(20.sp)

    val articleFontSizeProps = listOf(
        ::articleTitle,
        ::articleSub,
        ::articleBody,
        ::articleCaption,
        ::articleSection
    )

    fun increment() {
        if (currArticleSize >= MAX_INCREMENT) return
        articleFontSizeProps.forEach { prop ->
            val currentSize = prop.get().value
            prop.set((currentSize + 1f).sp)
        }
        currArticleSize++
    }

    fun decrement() {
        if (currArticleSize <= MIN_INCREMENT) return
        articleFontSizeProps.forEach { prop ->
            val currentSize = prop.get().value
            prop.set((currentSize - 1f).sp)
        }
        currArticleSize--
    }

    fun getCurrArticleSize() = currArticleSize

    fun setArticleSize(size: Int) {
        if (size !in MIN_INCREMENT..MAX_INCREMENT) return
        val delta = size - currArticleSize
        if (delta == 0) return
        articleFontSizeProps.forEach { prop ->
            val currentSize = prop.get().value
            prop.set((currentSize + delta).sp)
        }
        currArticleSize = size
    }
}