package com.ycngmn.prothomalo.ui.screens.settings.rearrange

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ycngmn.prothomalo.R
import com.ycngmn.prothomalo.prothomalo.PaloGlobal
import com.ycngmn.prothomalo.prothomalo.containers.HomeSectionContainer
import com.ycngmn.prothomalo.ui.assets.FontSizes
import com.ycngmn.prothomalo.ui.assets.Strings
import com.ycngmn.prothomalo.ui.assets.TextStyles
import com.ycngmn.prothomalo.ui.screens.home.HomeSectionDao
import com.ycngmn.prothomalo.ui.screens.topic.TopicTopBar
import com.ycngmn.prothomalo.utils.DraggableGrid
import java.util.AbstractMap.SimpleEntry

const val UNCHECKED_SUFFIX = "_UNCHECKED"

@Composable
fun RearrangeHome (navController: NavController, sectionDao: HomeSectionDao) {

    val defaultSections = PaloGlobal.getPalo().homeSections.entries.toList()

    var sectionContainer by remember {
        mutableStateOf(
            HomeSectionContainer(
                PaloGlobal.paloKey,
                emptyMap()
            )
        )
    }

    LaunchedEffect(Unit) { sectionContainer = sectionDao.getSection(PaloGlobal.paloKey)!! }

    if (sectionContainer.homeSections.isEmpty()) return

    val sections by remember { mutableStateOf(sectionContainer.homeSections.entries.toList()) }

    var allSections by remember { mutableStateOf(
        sections + defaultSections.filter { it.key !in sections.map { it.key } }) }

    LaunchedEffect(allSections) {
        val sectionContainer = HomeSectionContainer(
            PaloGlobal.paloKey,
            allSections.associate { it.key to it.value }
        )
        sectionDao.insertSection(sectionContainer)
    }

    val onMove: (Int, Int) -> Unit = { from, to ->
        allSections = allSections.toMutableList().apply { add(to, removeAt(from)) }
    }

    Scaffold (
        topBar = { TopicTopBar(Strings.get("section_rearrange")) { navController.popBackStack() } }
    ) { paddingValues ->
        Column (modifier = Modifier.padding(paddingValues)
            .background(MaterialTheme.colorScheme.background)) {


            Button(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 20.dp),
                onClick = { allSections = defaultSections }
            ) {


                Icon(
                    painter = painterResource(id = R.drawable.mop_24px),
                    contentDescription = null,
                    modifier = Modifier.padding(end = 8.dp),
                )

                Text("... ",
                    fontSize = 26.sp)

                Text(
                    text = Strings.get("reset_section"),
                    style = TextStyles.defaultTS(),
                    fontSize = FontSizes.rearrangeSection,
                    modifier = Modifier.padding(top = 3.dp)
                )

            }

            DraggableGrid(items = allSections.map { it.value }, onMove = onMove) { item, index, isDragging ->
                val elevation by animateDpAsState(if (isDragging) 4.dp else 1.dp, label = "elevation")
                DragItem(
                    item = item.replace(UNCHECKED_SUFFIX, ""),
                    elevation = elevation,
                    isChecked = !item.endsWith(UNCHECKED_SUFFIX),
                    isEnabled = item != defaultSections.first().value,
                    onCheckedChange = { checked ->
                        allSections = allSections.toMutableList().apply {
                            val baseValue = item.removeSuffix(UNCHECKED_SUFFIX)
                            val newValue = if (checked) baseValue else baseValue + UNCHECKED_SUFFIX
                            set(index, SimpleEntry(get(index).key, newValue))
                        }
                    }
                )
            }

        }
    }
}