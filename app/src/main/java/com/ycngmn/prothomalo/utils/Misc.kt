package com.ycngmn.prothomalo.utils

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ycngmn.prothomalo.ui.theme.PaloBlue
import com.ycngmn.prothomalo.ui.theme.PaloOrange
import com.ycngmn.prothomalo.ui.theme.PaloRed

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