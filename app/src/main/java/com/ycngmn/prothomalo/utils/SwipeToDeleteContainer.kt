package com.ycngmn.prothomalo.utils

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlin.math.abs

// credit : https://github.com/fjr619

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeToDeleteContainer(
    modifier: Modifier = Modifier,
    onDelete: () -> Unit,
    content: @Composable () -> Unit
) {
    val density = LocalDensity.current
    val icon: ImageVector = Icons.Outlined.Delete
    val alignment: Alignment = Alignment.CenterEnd
    var isRemoved by remember { mutableStateOf(false) }
    val threshold by remember { mutableStateOf(135.dp) }
    var offset by remember { mutableFloatStateOf(0f) }
    val swipeState = rememberSwipeToDismissBoxState(
        confirmValueChange = {
            if (it == SwipeToDismissBoxValue.EndToStart) {
                isRemoved = true
            }
            true
        },
        positionalThreshold = with(density) {
            { threshold.toPx()}
        }
    )
    val offsetMatch by remember(offset) {
        derivedStateOf {
            mutableStateOf(
                abs(offset) >= with(density) {
                    { threshold.toPx() }
                }.invoke()
            ).value
        }
    }

    val color by animateColorAsState(
        targetValue = if (swipeState.dismissDirection == SwipeToDismissBoxValue.EndToStart && offsetMatch) {
            MaterialTheme.colorScheme.errorContainer
        } else {
            MaterialTheme.colorScheme.surfaceContainer
        },
        label = ""
    )

    val iconSize by animateDpAsState(
        targetValue = if (offsetMatch) { 35.dp } else { 24.dp }, label = ""
    )

    LaunchedEffect(key1 = isRemoved) {
        if (isRemoved) {
            delay(250.toLong())
            onDelete()
        }
    }

    SwipeToDismissBox(
        modifier = modifier
            .animateContentSize()
            .onSizeChanged { offset = swipeState.requireOffset() },
        enableDismissFromStartToEnd = false,
        state = swipeState,
        backgroundContent = {
            Box(
                modifier = Modifier.fillMaxSize().background(color),
                contentAlignment = alignment
            ) {
                Box(
                    modifier = Modifier.size(40.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        modifier = Modifier.size(iconSize),
                        imageVector = icon, contentDescription = null
                    )
                }
            }
        }
    ) { content() }
}