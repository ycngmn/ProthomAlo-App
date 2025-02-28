package com.ycngmn.prothomalo.utils

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.sp
import com.ycngmn.prothomalo.scraper.ShurjoFamily

/* Initial Credit: https://stackoverflow.com/a/75977854/11724248
* Modified to make the indicator respond to swipes */

@Composable
fun CustomScrollableTabRow(
    tabs: List<String>,
    pagerState: PagerState,
    onTabClick: (Int) -> Unit
) {
    val density = LocalDensity.current
    val tabWidths = remember {
        mutableStateListOf<Dp>().apply {
            repeat(tabs.size) { add(0.dp) }
        }
    }

    ScrollableTabRow(
        selectedTabIndex = pagerState.currentPage,
        contentColor = MaterialTheme.colorScheme.onBackground,
        containerColor = MaterialTheme.colorScheme.background,
        edgePadding = 2.dp,
        indicator = { tabPositions ->
            val nextPage = (pagerState.currentPage + 1).coerceAtMost(tabs.size - 1)
            val offsetFraction = pagerState.currentPageOffsetFraction

            TabRowDefaults.SecondaryIndicator(
                modifier = Modifier.customTabIndicatorOffset(
                    currentTabPosition = tabPositions[pagerState.currentPage],
                    nextTabPosition = tabPositions.getOrNull(nextPage),
                    currentTabWidth = tabWidths[pagerState.currentPage],
                    nextTabWidth = tabWidths.getOrNull(nextPage),
                    offsetFraction = offsetFraction
                ),
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    ) {
        tabs.forEachIndexed { index, tab ->
            Tab(
                selected = pagerState.currentPage == index,
                onClick = { onTabClick(index) },
                text = {
                    Text(
                        text = tab,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontFamily = ShurjoFamily,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        onTextLayout = { result ->
                            tabWidths[index] = with(density) { result.size.width.toDp() }
                        }
                    )
                }
            )
        }
    }
}



fun Modifier.customTabIndicatorOffset(
    currentTabPosition: TabPosition,
    nextTabPosition: TabPosition?,
    currentTabWidth: Dp,
    nextTabWidth: Dp?,
    offsetFraction: Float
): Modifier {
    val targetOffset = if (nextTabPosition != null) {
        lerp(
            (currentTabPosition.left + currentTabPosition.right - currentTabWidth) / 2,
            (nextTabPosition.left + nextTabPosition.right - (nextTabWidth ?: currentTabWidth)) / 2,
            offsetFraction
        )
    } else {
        (currentTabPosition.left + currentTabPosition.right - currentTabWidth) / 2
    }

    val targetWidth = if (nextTabWidth != null) {
        lerp(currentTabWidth, nextTabWidth, offsetFraction)
    } else {
        currentTabWidth
    }

    return this.fillMaxWidth()
        .wrapContentSize(Alignment.BottomStart)
        .offset(x = targetOffset)
        .width(targetWidth)
}
