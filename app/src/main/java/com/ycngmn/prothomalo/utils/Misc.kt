package com.ycngmn.prothomalo.utils

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ycngmn.prothomalo.prothomalo.subs.PaloKeys
import com.ycngmn.prothomalo.ui.theme.PaloBlue
import com.ycngmn.prothomalo.ui.theme.PaloOrange
import com.ycngmn.prothomalo.ui.theme.PaloRed

fun getPaloKeyFromHost(host: String?): PaloKeys? {
    return when (host?.lowercase()) {
        "www.prothomalo.com"     -> PaloKeys.PaloMain
        "en.prothomalo.com"      -> PaloKeys.PaloEnglish
        "www.kishoralo.com"      -> PaloKeys.KishorAlo
        "www.bigganchinta.com"   -> PaloKeys.BigganChinta
        "1971.prothomalo.com"    -> PaloKeys.Mukti1971
        "www.bondhushava.com"    -> PaloKeys.BondhuShava
        "trust.prothomalo.com"   -> PaloKeys.PaloTrust
        "nagorik.prothomalo.com" -> PaloKeys.Nagorik
        else -> null
    }
}

@Composable
fun SetStatusBarColor() {
    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(
        color = MaterialTheme.colorScheme.background
    )
}

@Composable
fun selectColorByIndex (index: Int): Color {
    return when (index) {
        0 -> PaloRed
        1 -> PaloBlue
        2 -> PaloOrange

        else -> MaterialTheme.colorScheme.onBackground
    }
}