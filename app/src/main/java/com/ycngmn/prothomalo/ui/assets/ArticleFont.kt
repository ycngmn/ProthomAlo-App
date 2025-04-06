package com.ycngmn.prothomalo.ui.assets

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.ycngmn.prothomalo.prothomalo.ShurjoFamily

object ArticleFont {

    private val defaultTS = TextStyle(fontFamily = ShurjoFamily)
    private var currArticleSize = 0

    private const val MAX_INCREMENT = 5
    private const val MIN_INCREMENT = -5

    var articleTitleTS by mutableStateOf(
        defaultTS.copy(
            fontWeight = FontWeight.Companion.Bold,
            fontSize = 25.sp,
            lineHeight = 33.sp
        )
    )

    var articleSubTS by mutableStateOf(
        defaultTS.copy(
            fontWeight = FontWeight.Companion.Normal,
            fontSize = 17.sp
        )
    )

    var articleBodyTS by mutableStateOf(
        defaultTS.copy(
            fontWeight = FontWeight.Companion.Normal,
            fontSize = 18.sp,
            textAlign = TextAlign.Companion.Start
        )
    )

    var articleCaptionTS by mutableStateOf(
        articleBodyTS.copy(
            fontSize = 15.sp
        )
    )

    var articleSectionTS by mutableStateOf(
        defaultTS.copy(
            fontWeight = FontWeight.Companion.Bold,
            fontSize = 20.sp,
        )
    )

    val articleStyleProps = listOf(
        ::articleTitleTS,
        ::articleSubTS,
        ::articleBodyTS,
        ::articleCaptionTS,
        ::articleSectionTS
    )

    private fun scaleTextStyle(style: TextStyle, scale: Float): TextStyle {
        return style.copy(
            fontSize = (style.fontSize.value + scale).sp,
            lineHeight = if (style.lineHeight != TextUnit.Companion.Unspecified)
                    (style.lineHeight.value + scale).sp
            else TextUnit.Companion.Unspecified
        )
    }

    fun increment() {
        if (currArticleSize == MAX_INCREMENT) return
        for (prop in articleStyleProps) {
            prop.set(scaleTextStyle(prop.get(), 1f))
        }
        currArticleSize++
    }

    fun decrement() {
        if (currArticleSize == MIN_INCREMENT) return
        for (prop in articleStyleProps) {
            prop.set(scaleTextStyle(prop.get(), -1F))
        }
        currArticleSize--
    }

    fun getCurrArticleSize() = currArticleSize

    fun setArticleSize(size: Int) {
        currArticleSize = size
        for (prop in articleStyleProps) {
            prop.set(scaleTextStyle(prop.get(), size.toFloat()))
        }
    }


}