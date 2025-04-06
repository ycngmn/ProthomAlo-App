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
import androidx.compose.foundation.text.selection.DisableSelection
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.ycngmn.prothomalo.R
import com.ycngmn.prothomalo.prothomalo.NewsContainer
import com.ycngmn.prothomalo.ui.screens.bookmark.BookmarkDao
import com.ycngmn.prothomalo.ui.screens.bookmark.onBookmarkButtonClick
import com.ycngmn.prothomalo.ui.screens.settings.SettingsVM
import com.ycngmn.prothomalo.ui.theme.PaloBlue
import com.ycngmn.prothomalo.ui.assets.ArticleFont
import com.ycngmn.prothomalo.utils.FormatTime


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun NewsHead(
    news: NewsContainer,
    bookmarkDao: BookmarkDao,
    settingsVM: SettingsVM,
    onTopicClick: (String) -> Unit,
) {
    val context = LocalContext.current
    val isExist = bookmarkDao.checkBookmarkFlow(news.newsUrl).collectAsState(false)

    Column (Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {

        DisableSelection {
            Text(
                text = news.section,
                Modifier.padding(start = 16.dp, top = 10.dp, bottom = 10.dp).drawBehind {
                    val height = size.height
                    drawLine(
                        color = PaloBlue,
                        start = Offset(0f, height + 10),
                        end = Offset(size.width, height + 10),
                        strokeWidth = 4f
                    )
                }.clickable { onTopicClick(news.sectionSlug + "@" + news.section) },
                style = ArticleFont.articleSectionTS,
                color = PaloBlue,
            )
        }

        Text(
            text = news.headline,
            Modifier.padding(16.dp, 7.dp, 25.dp, 10.dp),
            style = ArticleFont.articleTitleTS,
            color = MaterialTheme.colorScheme.onBackground
        )

        if (news.summary!="null")
            Text(
                text = news.summary!!,
                Modifier.padding(horizontal = 16.dp),
                style = ArticleFont.articleBodyTS,
                color = Color.Gray,

            )


        FlowRow (modifier = Modifier.padding(vertical = 10.dp, horizontal = 16.dp ).fillMaxWidth()){

            if (news.author != "null") {
                var authorText = news.author!!

                if (news.authorLocation != "null" && news.authorLocation.isNotEmpty())
                    authorText = authorText.plus(
                        (if (authorText.trim().endsWith(":")) "" else ",") +
                            " ${news.authorLocation}")

                Text(
                    text = authorText,
                    style = ArticleFont.articleSubTS,
                    color = MaterialTheme.colorScheme.onBackground,
                    overflow = TextOverflow.Ellipsis,
                )
            }

            Spacer(Modifier.weight(1F))

            Text(
                text = FormatTime.toAgoString(news.date, news.newsUrl),
                style = ArticleFont.articleSubTS,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        Row (Modifier.padding(bottom = 20.dp, start = 14.dp, end = 14.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {

            Icon(
                painter = painterResource(R.drawable.text_increase_40px),
                contentDescription = "Increase text size",
                modifier = Modifier.size(25.dp)
                    .clickable {
                        ArticleFont.increment()
                        settingsVM.setArticleTextSize(ArticleFont.getCurrArticleSize())
                               },
                tint = if (ArticleFont.getCurrArticleSize() == 5)
                    Color.Gray else MaterialTheme.colorScheme.onBackground
            )
            Icon(
                painter = painterResource(R.drawable.text_decrease_40px),
                contentDescription = "Decrease text size",
                modifier = Modifier.size(25.dp)
                    .clickable {
                        ArticleFont.decrement()
                        settingsVM.setArticleTextSize(ArticleFont.getCurrArticleSize())
                               },
                tint = if (ArticleFont.getCurrArticleSize() == -5)
                    Color.Gray else MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.weight(1F))

            Icon(
                painter = if (isExist.value) painterResource(R.drawable.bookmark_remove_24px)
                else painterResource(R.drawable.bookmark_add_24px),
                contentDescription = "Bookmark article",
                modifier = Modifier.size(25.dp)
                    .clickable { onBookmarkButtonClick(context, news, bookmarkDao) },
                tint = MaterialTheme.colorScheme.onBackground,
            )

            DisableSelection { ShareSheet(news) }
        }

        HorizontalDivider(modifier = Modifier
            .padding(16.dp, 5.dp, 10.dp, 5.dp)
            .width(35.dp),
            thickness = 3.dp,
        )
    }
}
