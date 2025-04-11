package com.ycngmn.prothomalo.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.ycngmn.prothomalo.prothomalo.PaloGlobal
import com.ycngmn.prothomalo.prothomalo.PaloVM
import com.ycngmn.prothomalo.prothomalo.containers.HomeSectionContainer
import com.ycngmn.prothomalo.ui.animation.LoadingAnimation
import com.ycngmn.prothomalo.ui.screens.article.NewsViewModel
import com.ycngmn.prothomalo.ui.screens.settings.rearrange.UNCHECKED_SUFFIX

@Composable
fun HomePage(
    navController: NavController,
    newsViewModel: NewsViewModel,
    homeSectionDao: HomeSectionDao
) {

    var sections by remember { mutableStateOf(
        HomeSectionContainer(PaloGlobal.paloKey, emptyMap())
    ) }

    LaunchedEffect(PaloGlobal.paloKey) {
        val existing = homeSectionDao.getSection(PaloGlobal.paloKey)
        if (existing != null && existing.homeSections.isNotEmpty()) {
            sections = existing
        } else {
            val fallback = HomeSectionContainer(
                paloKey = PaloGlobal.paloKey,
                homeSections = PaloGlobal.getPalo().homeSections
            )
            homeSectionDao.insertSection(fallback)
            sections = fallback
        }
    }

    if (sections.homeSections.isEmpty()) {
        // Show the loading screen only at first launch
        if (navController.previousBackStackEntry == null)
            LoadingAnimation()
        return
    }

    val filteredSections by remember { mutableStateOf(
        sections.homeSections.filterNot { it.value.endsWith(UNCHECKED_SUFFIX) }) }

    val keys = remember { filteredSections.keys.toList() }
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { filteredSections.size })

    Scaffold(
        topBar = {
            Surface(shadowElevation = 3.dp) {
                TopBar(pagerState, filteredSections.values.toList())
            }
        },
        bottomBar = { BottomBar(navController) }
    )
    {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .background(MaterialTheme.colorScheme.background)
        ) {
            HorizontalPager(state = pagerState) { page ->
                val articleVM = viewModel(key = PaloGlobal.getPalo().webUrl + keys[page]) {
                    PaloVM(keys[page])
                }
                NewsColumn(articleVM, navController, newsViewModel)
            }
        }
    }
}