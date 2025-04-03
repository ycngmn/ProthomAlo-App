package com.ycngmn.prothomalo.ui.screens.error

import androidx.compose.runtime.Composable
import com.ycngmn.prothomalo.R

@Composable
fun ErrorConnection(onClick: () -> Unit) {
    ErrorPage(R.drawable.error_conn, "সংযোগ ব্যর্থ হয়েছে", "", "আবার চেষ্টা করুন") { onClick() }
}