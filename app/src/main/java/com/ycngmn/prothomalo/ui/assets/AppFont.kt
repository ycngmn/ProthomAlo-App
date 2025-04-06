package com.ycngmn.prothomalo.ui.assets

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.ycngmn.prothomalo.R

val ShurjoFamily = FontFamily(
    Font(R.font.shurjo_regular, FontWeight.Normal),
    Font(R.font.shurjo_bold, FontWeight.Bold)
)

enum class AppFontSize {
    SMALL, MEDIUM, LARGE
}

object AppFont {
    private val defaultTS = TextStyle(fontFamily = ShurjoFamily)
    private var currFontSize = AppFontSize.MEDIUM

    var subHeadSS by mutableStateOf(SpanStyle(color = Color.Gray, fontSize = 14.sp))
    var titleTS by mutableStateOf(defaultTS.copy(fontSize = 18.sp, fontWeight = FontWeight.Bold))
    var titleTS2 by mutableStateOf(titleTS.copy(fontSize = 22.sp))
    var dateTS by mutableStateOf(defaultTS.copy(fontSize = 13.sp, color = Color.Gray))
    var dateTS2 by mutableStateOf(dateTS.copy(fontSize = 16.sp))
    var bottomSheetTS by mutableStateOf(defaultTS.copy(fontSize = 20.sp))
    var emptyBookmarkTS by mutableStateOf(defaultTS.copy(fontSize = 25.sp, textAlign = TextAlign.Center))
    var errorTitleTS by mutableStateOf(titleTS.copy(fontSize = 35.sp))
    var errorSubTitleTS by mutableStateOf(emptyBookmarkTS.copy(fontSize = 18.sp))
    var navBottomTS by mutableStateOf(titleTS.copy(fontSize = 11.sp))
    var searchHintTS by mutableStateOf(dateTS.copy(fontSize = 17.sp, fontWeight = FontWeight.Bold))
    var tabRowTS by mutableStateOf(defaultTS.copy(fontSize = 14.sp))

    val appFontProps = listOf(
        ::titleTS, ::titleTS2,
        ::dateTS, ::dateTS2,
        ::bottomSheetTS, ::emptyBookmarkTS,
        ::errorTitleTS, ::errorSubTitleTS,
        ::navBottomTS, ::searchHintTS,
        ::tabRowTS
    )

    private fun scaleTextStyle(style: TextStyle, scale: Float): TextStyle {
        return style.copy(
            fontSize = (style.fontSize.value + scale).sp,
            lineHeight = if (style.lineHeight != TextUnit.Companion.Unspecified)
                (style.lineHeight.value + scale).sp
            else TextUnit.Companion.Unspecified
        )
        subHeadSS = subHeadSS.copy(fontSize = (subHeadSS.fontSize.value + scale).sp)
    }

    fun setSmall() {
        if (currFontSize == AppFontSize.SMALL) return
        val scale = if (currFontSize == AppFontSize.MEDIUM) -2f else -4f
        for (prop in appFontProps) {
            prop.set(scaleTextStyle(prop.get(), scale))
        }
        currFontSize = AppFontSize.SMALL
    }

    fun setMedium() {
        if (currFontSize == AppFontSize.MEDIUM) return
        val scale = if (currFontSize == AppFontSize.SMALL) 2f else -2f
        for (prop in appFontProps) {
            prop.set(scaleTextStyle(prop.get(), scale))
        }
        currFontSize = AppFontSize.MEDIUM
    }

    fun setLarge() {
        if (currFontSize == AppFontSize.LARGE) return
        val scale = if (currFontSize == AppFontSize.MEDIUM) 2f else 4f
        for (prop in appFontProps) {
            prop.set(scaleTextStyle(prop.get(), scale))
        }
        currFontSize = AppFontSize.LARGE
    }

    fun setAppFontSize(size: AppFontSize) {
        when (size) {
            AppFontSize.SMALL -> setSmall()
            AppFontSize.MEDIUM -> setMedium()
            AppFontSize.LARGE -> setLarge()
        }
    }
}