package com.ycngmn.prothomalo.ui.screens.topic

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.ycngmn.prothomalo.ui.assets.FontSizes
import com.ycngmn.prothomalo.ui.assets.TextStyles

@Composable
fun TopicTopBar(barTitle: String, onBackPressed : () -> Unit) {
    Surface (
        modifier = Modifier.background(MaterialTheme.colorScheme.background),
        shadowElevation = 4.dp
    ) {
        Row {
            Box(modifier = Modifier.fillMaxWidth().padding(10.dp)) {

                Icon(
                    Icons.AutoMirrored.Rounded.ArrowBack,
                    contentDescription = "Account_and_Setting_logo",
                    modifier = Modifier.size(28.dp).align(Alignment.CenterStart)
                        .clickable { onBackPressed() }
                )

                val onBg = MaterialTheme.colorScheme.onBackground
                Text(
                    modifier = Modifier.align(Alignment.Center).padding(horizontal = 40.dp)
                        .drawBehind {
                            drawLine(
                                color = onBg,
                                start = Offset(0f, size.height + 6),
                                end = Offset(size.width, size.height + 6),
                                strokeWidth = 4f
                            )
                        },
                    text = barTitle,
                    fontSize = FontSizes.topicTopBar,
                    style = TextStyles.topicTopBar,
                    fontWeight = FontWeight.Bold,
                    color = onBg,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}