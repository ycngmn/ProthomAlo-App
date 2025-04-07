package com.ycngmn.prothomalo.ui.assets

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

object TextStyles {

    private val defaultTS = TextStyle(fontFamily = ShurjoFamily)
    val bottomNavBar = defaultTS.copy(fontWeight = FontWeight.Bold)
    val articleCardDate = defaultTS.copy(color = Color.Gray)
    val gitCreditSub = defaultTS.copy(color = Color.Gray)
    val settingsTileSub = defaultTS.copy(color = Color.Gray)
    val articleCardV2Date = defaultTS.copy(color = Color.Gray)
    val expandedMenuItem = defaultTS
    val filterDropDownItem = defaultTS
    val tabRowTitle = defaultTS
    val themeDialogOption = defaultTS
    val expandableMenuItem = defaultTS
    val bottomSheetBtn = defaultTS
    val settingTileTitle = defaultTS
    val topicTopBar = defaultTS
    val sectionCard = defaultTS
    val searchPrompt = defaultTS.copy(fontWeight = FontWeight.Bold)
    val searchHint = searchPrompt.copy(fontSize = FontSizes.searchPrompt)
    val themeConfirmBtn = defaultTS.copy(fontWeight = FontWeight.Bold)
    val articleCardTitle = defaultTS.copy(fontWeight = FontWeight.Bold)
    val articleCardV2Title = defaultTS.copy(fontWeight = FontWeight.Bold)
    val themeAlertTitle = defaultTS.copy(fontWeight = FontWeight.Bold)
    val errorTitle = defaultTS.copy(fontWeight = FontWeight.Bold)
    val githubCreditTitle = defaultTS.copy(fontWeight = FontWeight.Bold)
    val filterHint = defaultTS.copy(fontWeight = FontWeight.Bold)
    val emptyBookmark = defaultTS.copy(textAlign = TextAlign.Center)
    val errorSub = defaultTS.copy(textAlign = TextAlign.Center)

    fun defaultTS() = defaultTS
}