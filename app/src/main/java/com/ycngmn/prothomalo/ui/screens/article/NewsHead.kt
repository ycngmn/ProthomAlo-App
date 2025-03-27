package com.ycngmn.prothomalo.ui.screens.article

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ycngmn.prothomalo.R
import com.ycngmn.prothomalo.scraper.NewsContainer
import com.ycngmn.prothomalo.scraper.ShurjoFamily
import com.ycngmn.prothomalo.ui.theme.PaloBlue


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun NewsHead(
    news: NewsContainer,
    onTopicClick: (String) -> Unit,
) {
    Column (Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {

        Text(
            text = news.section,
            Modifier.padding(start = 16.dp, top = 10.dp, bottom = 10.dp).drawBehind {
                val height = size.height
                drawLine(
                    color = PaloBlue,
                    start = Offset(0f, height+10),
                    end = Offset(size.width, height+10),
                    strokeWidth = 4f
                )
            }.clickable { onTopicClick(news.sectionSlug + "@" + news.section) },
            fontFamily = ShurjoFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp,
            color = PaloBlue,
        )

        Text(
            text = news.headline,
            Modifier.padding(16.dp, 7.dp, 25.dp, 10.dp),
            fontFamily = ShurjoFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp,
            lineHeight = 33.sp,
            color = MaterialTheme.colorScheme.onBackground
        )

        if (news.summary!="null")
            Text(
                text = news.summary!!,
                Modifier.padding(horizontal = 16.dp),
                fontFamily = ShurjoFamily,
                fontWeight = FontWeight.Normal,
                color = Color.Gray,
                fontSize = 18.sp
            )


        FlowRow (modifier = Modifier.padding(vertical = 10.dp, horizontal = 16.dp ).fillMaxWidth()){

            if (news.author != "null") {
                val authorText = news.author!!

                if (news.authorLocation != "null" && news.authorLocation.isNotEmpty())
                    authorText.plus(", $news.authorLocation")

                Text(
                    text = authorText,
                    fontFamily = ShurjoFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 17.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            Spacer(Modifier.weight(1F))

            Text(
                text = news.date,
                fontFamily = ShurjoFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 17.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        Row (Modifier.padding(bottom = 20.dp, start = 14.dp, end = 14.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {

            Icon(
                painter = painterResource(R.drawable.text_increase_40px),
                contentDescription = "Bookmark article",
                modifier = Modifier.size(30.dp)
                    .clickable { TODO() },
                tint = MaterialTheme.colorScheme.onBackground,
            )
            Icon(
                painter = painterResource(R.drawable.text_decrease_40px),
                contentDescription = "Bookmark article",
                modifier = Modifier.size(25.dp),
                tint = MaterialTheme.colorScheme.onBackground,
            )

            Spacer(modifier = Modifier.weight(1F))

            Icon(
                painter = painterResource(R.drawable.bookmark_icon),
                contentDescription = "Bookmark article",
                modifier = Modifier.size(25.dp)
                    .clickable { TODO() },
                tint = MaterialTheme.colorScheme.onBackground,
            )

            Icon(
                painter = painterResource(R.drawable.share_windows_40px),
                contentDescription = "Share article",
                modifier = Modifier.size(25.dp)
                    .clickable { TODO() },
                tint = MaterialTheme.colorScheme.onBackground,
            )
        }

        HorizontalDivider(modifier = Modifier
            .padding(16.dp, 5.dp, 10.dp)
            .width(35.dp),
            thickness = 3.dp,
        )

    }
}
