package com.ycngmn.prothomalo.ui.screens.error

import androidx.compose.runtime.Composable
import com.ycngmn.prothomalo.R
import com.ycngmn.prothomalo.ui.assets.Strings

@Composable
fun ErrorConnection(onClick: () -> Unit) {
    ErrorPage(
        R.drawable.error_conn,
        Strings.get("error_connection_title"),
        Strings.get("error_connection_sub"),
        Strings.get("error_connection_button"),
        onClick
    )
}