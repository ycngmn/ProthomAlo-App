package com.ycngmn.prothomalo.ui.screens.menu

import androidx.compose.animation.animateContentSize
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.ycngmn.prothomalo.ui.assets.AppFont

@Composable
fun ExpandableItem(
    navController: NavHostController,
    title: String,
    isExpandable: Boolean,
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
                style = AppFont.titleTS.copy(fontWeight = FontWeight.Normal),
                color = MaterialTheme.colorScheme.onBackground,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.weight(1f))
            if (isExpandable) {

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

        }

        if (expanded) {

            Column (modifier = Modifier.padding(start = 15.dp, top = 20.dp)) {
                sections.forEachIndexed { index, section ->
                    Text(
                        section.first,
                        style = AppFont.dateTS2,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.fillMaxWidth().clickable {
                            navController.navigate("topic/${section.second}@${section.first}")
                        })
                    if (index < sections.size-1)
                        HorizontalDivider(Modifier.padding(vertical = 12.dp), thickness = 0.3.dp)
                }
            }
        }
        HorizontalDivider(Modifier.padding(top = 10.dp), thickness = 0.2.dp, color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f))
    }
}