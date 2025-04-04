package com.ycngmn.prothomalo.ui.screens.error

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.ycngmn.prothomalo.R

@Composable
fun Error404(navController: NavController) {

    ErrorPage(
        R.drawable.error_404,
        stringResource(R.string.error_404_title),
        stringResource(R.string.error_404_sub),
        stringResource(R.string.error_404_button)) {
        navController.navigateUp()
    }
}