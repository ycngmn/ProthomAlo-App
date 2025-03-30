package com.ycngmn.prothomalo.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ycngmn.prothomalo.prothomalo.PaloGlobal
import com.ycngmn.prothomalo.utils.CustomScrollableTabRow
import kotlinx.coroutines.launch

@Composable
fun TopBar(pageState: PagerState) {

    val coroutineScope = rememberCoroutineScope()
    val isLightTheme = !PaloGlobal.isDarkTheme

    Column (
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.SpaceBetween
    )
    {

        Box(modifier = Modifier.padding(start = 10.dp, bottom = 3.dp)) {
            Image(
                modifier = Modifier.width(150.dp).height(40.dp),
                painter = painterResource(
                    if (isLightTheme) PaloGlobal.getPalo().dayLogo
                    else PaloGlobal.getPalo().nightLogo
                ),
                contentDescription = "ProthomAlo_Logo",
                contentScale = ContentScale.Fit
            )
        }

        val articleSections = PaloGlobal.getPalo().articleSections.values.toList()

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