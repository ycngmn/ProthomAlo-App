package com.ycngmn.prothomalo.ui.screens.bookmark

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ycngmn.prothomalo.R
import com.ycngmn.prothomalo.Strings
import com.ycngmn.prothomalo.prothomalo.ShurjoFamily
import com.ycngmn.prothomalo.ui.theme.PaloBlue
import com.ycngmn.prothomalo.ui.theme.PaloRed

@Composable
fun EmptyBookmarkScreen(onClick: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        val annotatedString = buildAnnotatedString {
            append(Strings.get("empty_bookmark_1"))
            appendInlineContent(id = "imageId")
            append(Strings.get("empty_bookmark_2"))
        }

        val inlineContentMap = mapOf(
            "imageId" to InlineTextContent(
                Placeholder(40.sp, 40.sp, PlaceholderVerticalAlign.TextCenter)
            ) {
                Icon(
                    painter = painterResource(R.drawable.bookmark_icon),
                    contentDescription = "Bookmark article",
                    tint = PaloRed,
                )
            }
        )


        Text(
            annotatedString,
            inlineContent = inlineContentMap,
            textAlign = TextAlign.Center,
            fontFamily = ShurjoFamily,
            fontSize = 25.sp,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(Modifier.height(100.dp))

        Text(
            text = Strings.get("empty_bookmark_button"),
            textAlign = TextAlign.Center,
            fontFamily = ShurjoFamily,
            fontSize = 25.sp,
            textDecoration = TextDecoration.Underline,
            color = PaloBlue,
            modifier = Modifier.clickable { onClick() }
        )

    }
}