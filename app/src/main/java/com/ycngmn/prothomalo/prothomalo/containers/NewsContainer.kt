package com.ycngmn.prothomalo.prothomalo.containers

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookmarks")
data class NewsContainer(
    val headline: String = "",
    val summary: String? = "",
    val newsUrl: String = "",
    val author: String? = "",
    val authorLocation: String = "",
    val date: Long = 0,
    val section: String = "",
    val sectionSlug: String = "",
    val body: List<Pair<String, String>> = emptyList(),
    val readAlso: List<ArticleContainer> = emptyList(),
    val readAlsoText: String = "",
    @PrimaryKey(autoGenerate = true) val newsId: Int = 0,
)