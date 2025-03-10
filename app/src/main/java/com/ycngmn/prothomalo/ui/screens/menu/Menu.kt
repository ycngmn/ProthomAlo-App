package com.ycngmn.prothomalo.ui.screens.menu

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.ycngmn.prothomalo.R
import com.ycngmn.prothomalo.scraper.ShurjoFamily
import com.ycngmn.prothomalo.ui.screens.home.BottomBar
import com.ycngmn.prothomalo.ui.theme.RealPaloRead

@Composable
fun MediaCard(iconRes: Int, text: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier.padding(16.dp).clickable { onClick() },
        shape = RoundedCornerShape(3.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
    ) {
        Row(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                painter = painterResource(id = iconRes),
                contentDescription = "",
                colorFilter = ColorFilter.tint(RealPaloRead),
                modifier = Modifier.padding(end = 6.dp).size(22.dp)
            )
            Text(
                text = text,
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal,
                fontFamily = ShurjoFamily,
                color = MaterialTheme.colorScheme.onBackground,
            )
        }
    }
}



@Composable
fun ExpandableItem(
    navController: NavHostController,
    title: String,
    expanded: Boolean,
    sections: List<Pair<String, String>>,
    onClick: () -> Unit,
    onExpand: () -> Unit) {


    Column (
        modifier = Modifier.fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {

        Row(
            modifier = Modifier.height(30.dp).animateContentSize()
                .clickable { onClick() },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {

            Text(
                text = title,
                modifier = Modifier,
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal,
                fontFamily = ShurjoFamily,
                color = MaterialTheme.colorScheme.onBackground,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.weight(1f))

            VerticalDivider(
                modifier = Modifier.height(15.dp),
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.1f)
            )
            Icon(
                if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                contentDescription = "Expand $title",
                modifier = Modifier.size(50.dp).padding(start = 10.dp)
                    .clickable { onExpand() }
            )

        }

        if (expanded) {

            Column (modifier = Modifier.padding(start = 15.dp, top = 20.dp)) {
                sections.forEachIndexed { index, section ->
                    Text(section.first, modifier = Modifier.fillMaxWidth().clickable { navController.navigate("topic/${section.second}@${section.first}") })
                    if (index<sections.size-1)
                        HorizontalDivider(Modifier.padding(vertical = 12.dp), thickness = 0.3.dp)
                }
            }
        }

        HorizontalDivider(Modifier.padding(top = 10.dp), thickness = 0.2.dp, color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f))
    }
}


@Composable
fun MenuScreen(navController: NavHostController) {

    val scrollState = rememberScrollState()
    var expandedStates by rememberSaveable { mutableStateOf(List(sectionMap.size) { false }) }
    var isSearchFilterVisible by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        bottomBar = { BottomBar(navController) }
    ) {
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(it).padding(16.dp)
                .verticalScroll(scrollState),
        ) {
            PaloSeachBar() { isSearchFilterVisible = !isSearchFilterVisible }

            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                MediaCard(
                    iconRes = R.drawable.photo_library_24px,
                    text = "ছবি"
                ) { navController.navigate("topic/home-photo@ছবি") }
                MediaCard(
                    iconRes = R.drawable.video_library_24px,
                    text = "ভিডিও"
                ) { navController.navigate("topic/video-all@ভিডিও") }
            }
            sectionMap.entries.forEachIndexed { index, (key, value) ->

                ExpandableItem(
                    navController,
                    title = key.first,
                    expanded = expandedStates[index],
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
                FilterBar()
            }

        }

    }

}

@Preview(showBackground = true)
@Composable
fun MenuTopBar() {
    Surface(
        modifier = Modifier.background(MaterialTheme.colorScheme.background).height(45.dp),
        shadowElevation = 4.dp
    ) {
        Row (modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center) {

            Text(
                modifier = Modifier
                    .padding(start = 12.dp),
                text = "তালিকা",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = ShurjoFamily,
                color = MaterialTheme.colorScheme.onBackground,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}