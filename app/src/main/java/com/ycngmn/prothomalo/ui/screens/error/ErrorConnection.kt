package com.ycngmn.prothomalo.ui.screens.error

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.ycngmn.prothomalo.R

@Composable
fun ErrorConnection(onClick: () -> Unit) {
    ErrorPage(
        R.drawable.error_conn,
        stringResource(R.string.error_connection_title),
        stringResource(R.string.error_connection_sub),
        stringResource(R.string.error_connection_button),
        onClick
    )
}