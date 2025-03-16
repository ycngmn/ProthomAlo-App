package com.ycngmn.prothomalo.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ycngmn.prothomalo.R
import com.ycngmn.prothomalo.scraper.ProthomAlo
import com.ycngmn.prothomalo.utils.CustomScrollableTabRow
import kotlinx.coroutines.launch

@Composable
fun TopBar(pageState: PagerState) {

    val coroutineScope = rememberCoroutineScope()

    Column (
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.SpaceBetween
    )
    {
        Row {
            Box (modifier = Modifier.fillMaxWidth().padding(start = 10.dp)) {
                Image(
                    painter = painterResource(R.drawable.main_logo_foreground),
                    contentDescription = "ProthomAlo_Logo",
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground)
                )
                Image(
                    painter = painterResource(R.drawable.main_logo_background),
                    contentDescription = "ProthomAlo_Logo_red",
                )
            }

        }

        Spacer(modifier = Modifier.height(10.dp))

        val articleSections = ProthomAlo().articleSections.values.toList()

        CustomScrollableTabRow (
            pagerState = pageState,
            tabs = articleSections
        ) {
            coroutineScope.launch {
                pageState.animateScrollToPage(it)
            }

        }
    }
}