package com.ycngmn.prothomalo.ui.screens.error

import com.ycngmn.prothomalo.ui.assets.Strings
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.ycngmn.prothomalo.R

@Composable
fun Error404(navController: NavController) {

    ErrorPage(
        R.drawable.error_404,
        Strings.get("error_404_title"),
        Strings.get("error_404_sub"),
        Strings.get("error_404_button")) {
        navController.navigateUp()
    }
}