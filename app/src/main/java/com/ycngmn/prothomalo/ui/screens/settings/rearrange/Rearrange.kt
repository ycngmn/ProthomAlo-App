package com.ycngmn.prothomalo.ui.screens.settings.rearrange

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ycngmn.prothomalo.prothomalo.PaloGlobal
import com.ycngmn.prothomalo.prothomalo.containers.HomeSectionContainer
import com.ycngmn.prothomalo.ui.assets.Strings
import com.ycngmn.prothomalo.ui.screens.home.HomeSectionDao
import com.ycngmn.prothomalo.ui.screens.topic.TopicTopBar
import com.ycngmn.prothomalo.utils.DraggableGrid


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

    LaunchedEffect(Unit) {
        val existing = sectionDao.getSection(PaloGlobal.paloKey)
        if (existing != null && existing.homeSections.isNotEmpty()) {
            sectionContainer = existing
        } else {
            val fallback = HomeSectionContainer(
                paloKey = PaloGlobal.paloKey,
                homeSections = defaultSections.associate { it.key to it.value }
            )
            sectionDao.insertSection(fallback)
            sectionContainer = fallback
        }
    }

    if (sectionContainer.homeSections.isEmpty()) return

    var sections by remember { mutableStateOf(sectionContainer.homeSections.entries.toList()) }

    LaunchedEffect(sections) {

        val sectionContainer = HomeSectionContainer(
            PaloGlobal.paloKey,
            sections.associate { it.key to it.value }
        )
        sectionDao.insertSection(sectionContainer)
    }

    //val allSections = sections + defaultSections.filter { it.key !in sections.map { it.key } }

    val onMove: (Int, Int) -> Unit = { from, to ->
        sections = sections.toMutableList().apply { add(to, removeAt(from)) }
    }


    Scaffold (
        topBar = { TopicTopBar(Strings.get("section_rearrange")) { navController.popBackStack() } }
    ) { paddingValues ->
        Column (modifier = Modifier.padding(paddingValues)
            .background(MaterialTheme.colorScheme.background)) {

            DraggableGrid(items = sections.map { it.value }, onMove = onMove) { item, isDragging ->
                val elevation by animateDpAsState(if (isDragging) 4.dp else 1.dp, label = "elevation")
                DragItem(
                    item = item,
                    elevation = elevation,
                    isChecked = item in sections.map { it.value },
                    isEnabled = item != defaultSections.first().value
                )
            }


        }
    }
}