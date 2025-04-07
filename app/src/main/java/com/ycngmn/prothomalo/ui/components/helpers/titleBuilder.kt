package com.ycngmn.prothomalo.ui.components.helpers

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import com.ycngmn.prothomalo.ui.assets.FontSizes

fun titleBuilder(subHead: String, title: String, subColor: Color, titleColor: Color): AnnotatedString {
    return buildAnnotatedString {
        if (subHead != "null") {
            withStyle(
                style = SpanStyle(color = subColor)
            ) { append(subHead) }

            withStyle(SpanStyle(color = Color.Gray, fontSize = FontSizes.articleCardDot)) {
                append(" ● ")
            }
        }
        withStyle(
            SpanStyle(color = titleColor)
        ) { append(title) }

    }
}