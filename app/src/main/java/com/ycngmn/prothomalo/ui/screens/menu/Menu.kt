package com.ycngmn.prothomalo.ui.screens.menu

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.ycngmn.prothomalo.R
import com.ycngmn.prothomalo.scraper.PaloGlobal
import com.ycngmn.prothomalo.ui.screens.home.BottomBar
import com.ycngmn.prothomalo.ui.screens.search.FilterBar
import com.ycngmn.prothomalo.ui.screens.search.PaloSearchBar
import com.ycngmn.prothomalo.ui.screens.search.SearchViewModel


@Composable
fun MenuScreen(navController: NavHostController, searchViewModel: SearchViewModel) {

    val sectionMap = PaloGlobal.getPalo().menuMap
    val scrollState = rememberScrollState()
    var expandedStates by rememberSaveable { mutableStateOf(List(sectionMap.size) { false }) }
    var isSearchFilterVisible by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        bottomBar = { BottomBar(navController) }
    ) {
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(it).padding(16.dp, 16.dp, 16.dp, 0.dp)
                .verticalScroll(scrollState),
        ) {
            PaloSearchBar(isSearchFilterVisible, searchViewModel,
                onBackPress = { isSearchFilterVisible = !isSearchFilterVisible },
                onSearch = {
                    navController.navigate("search")
                })

            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                SectionCards(
                    iconRes = R.drawable.photo_library_24px,
                    text = "ছবি"
                ) { navController.navigate("topic/home-photo@ছবি") }
                SectionCards(
                    iconRes = R.drawable.video_library_24px,
                    text = "ভিডিও"
                ) { navController.navigate("topic/video-all@ভিডিও") }
            }
            sectionMap.entries.forEachIndexed { index, (key, value) ->

                ExpandableItem(
                    navController,
                    title = key.first,
                    isExpandable = value.isNotEmpty(),
                    expanded = if (value.isNotEmpty()) expandedStates[index] else false,
                    onClick = {
                        navController.navigate("topic/${key.second}@${key.first}")
                    },
                    sections = value
                ) {
                    expandedStates = expandedStates.mapIndexed { i, isExpanded ->
                        if (i == index) !isExpanded else isExpanded }
                }
            }
        }
        if (isSearchFilterVisible) {
            Column {
                FilterBar(searchViewModel)
            }

        }

    }

}