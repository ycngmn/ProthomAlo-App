package com.ycngmn.prothomalo.ui.assets
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.ycngmn.prothomalo.R

val ShurjoFamily = FontFamily(
    Font(R.font.shurjo_regular, FontWeight.Normal),
    Font(R.font.shurjo_bold, FontWeight.Bold)
)

enum class AppFontSize {
    SMALL, MEDIUM, LARGE
}

object FontSizes {

    private var currFontSize = AppFontSize.MEDIUM


    private val baseScale = mapOf(
        AppFontSize.SMALL to 1f,
        AppFontSize.MEDIUM to 3f,
        AppFontSize.LARGE to 5f
    )

    var bottomNavBar by mutableStateOf(11.sp)
    var articleCardDate by mutableStateOf(13.sp)
    var gitCreditSub by mutableStateOf(13.sp)
    var articleCardDot by mutableStateOf(14.sp)
    var tabRowTitle by mutableStateOf(14.sp)
    var settingsTileSub by mutableStateOf(15.sp)
    var articleCardV2Date by mutableStateOf(16.sp)
    var expandedMenuItem by mutableStateOf(16.sp)
    var filterDropDownItem by mutableStateOf(16.sp)
    var searchPrompt by mutableStateOf(16.sp)
    var themeConfirmBtn by mutableStateOf(16.sp)
    var articleCardTitle by mutableStateOf(18.sp)
    var githubCreditTitle by mutableStateOf(18.sp)
    var filterHint by mutableStateOf(18.sp)
    var themeDialogOption by mutableStateOf(18.sp)
    var expandableMenuItem by mutableStateOf(18.sp)
    var sectionCard by mutableStateOf(18.sp)
    var errorSub by mutableStateOf(18.sp)
    var bottomSheetBtn by mutableStateOf(20.sp)
    var settingTileTitle by mutableStateOf(20.sp)
    var topicTopBar by mutableStateOf(20.sp)
    var articleCardV2Title by mutableStateOf(22.sp)
    var themeAlertTitle by mutableStateOf(22.sp)
    var emptyBookmark by mutableStateOf(25.sp)
    var errorTitle by mutableStateOf(35.sp)


    private val appFontSizes = listOf(
        ::bottomNavBar,
        ::articleCardDate,
        ::gitCreditSub,
        ::articleCardDot,
        ::tabRowTitle,
        ::settingsTileSub,
        ::articleCardV2Date,
        ::expandedMenuItem,
        ::filterDropDownItem,
        ::searchPrompt,
        ::themeConfirmBtn,
        ::articleCardTitle,
        ::githubCreditTitle,
        ::filterHint,
        ::themeDialogOption,
        ::expandableMenuItem,
        ::sectionCard,
        ::errorSub,
        ::bottomSheetBtn,
        ::settingTileTitle,
        ::topicTopBar,
        ::articleCardV2Title,
        ::themeAlertTitle,
        ::emptyBookmark,
        ::errorTitle
    )

    private fun getScale(targetSize: AppFontSize): Float =
        baseScale[targetSize]!! - baseScale[currFontSize]!!

    fun setAppFontSize(targetSize: AppFontSize) {
        if (currFontSize == targetSize) return
        val scale = getScale(targetSize)
        appFontSizes.forEach { fontSizeProp ->
            val currentSize = fontSizeProp.get().value
            fontSizeProp.set((currentSize + scale).sp)
        }
        currFontSize = targetSize
    }
}