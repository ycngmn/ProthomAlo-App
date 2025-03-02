package com.ycngmn.prothomalo.scraper

import android.net.Uri
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.ycngmn.prothomalo.R
import org.json.JSONObject
import org.jsoup.Jsoup
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

val ShurjoFamily = FontFamily(
    Font(R.font.shurjo_regular, FontWeight.Normal),
    Font(R.font.shurjo_bold, FontWeight.Bold)
)


data class ArticleContainer (
    val title: String = "",
    val thumbnail: String = "",
    val url: String = "",
    val date: String = "",
    val subHead: String = ""
)

data class NewsContainer(
    val headline: String,
    val summary: String?,
    val author: String?,
    val authorLocation: String = "",
    val date: String = "",
    val section: String = "",
    val sectionSlug: String,
    val body: List<Any>,
    val readAlso: List<ArticleContainer> = emptyList(),
    val readAlsoText: String = ""
)


class ProthomAlo {

    private val webUrl = "https://www.prothomalo.com"

    val articleSections = mapOf(
        "latest" to "সর্বশেষ",
        "politics" to "রাজনীতি",
        "bangladesh" to "বাংলাদেশ",
        "crime-bangladesh" to "অপরাধ",
        "world-all" to "বিশ্ব",
        "technology-all" to "প্রযুক্তি",
        "crime-bangladesh" to "অপরাধ",
        "world-all" to "বিশ্ব",
        "education-top5" to "শিক্ষা",
        "sports-all" to "খেলা",
        "entertainment-all" to "বিনোদন",
        "chakri-all" to "চাকরি",
        "lifestyle-all" to "জীবনযাপন",
        "religion-all" to "ধর্ম",
    )

    private fun Long.toBengaliNumber(): String = this.toString()
        .replace('0', '০')
        .replace('1', '১')
        .replace('2', '২')
        .replace('3', '৩')
        .replace('4', '৪')
        .replace('5', '৫')
        .replace('6', '৬')
        .replace('7', '৭')
        .replace('8', '৮')
        .replace('9', '৯')

    private fun formatTimeAgo(milliseconds: Long): String {
        val now = System.currentTimeMillis()
        val diff = now - milliseconds

        return when {
            diff < 3600000 -> "${(diff / 60000).toBengaliNumber()} মিনিট আগে"
            diff < 86400000 -> "${(diff / 3600000).toBengaliNumber()} ঘণ্টা আগে"
            diff < 259200000 -> "${(diff / 86400000).toBengaliNumber()} দিন আগে"
            else -> {
                val date = Date(milliseconds)
                val formatter = SimpleDateFormat("dd-MM-yyyy hh:mm", Locale("bn", "BD"))
                formatter.format(date)
            }
        }
    }

    fun getArticle(section : String, offset : Int = 0, limit : Int = 15) : List<ArticleContainer> {
        return extractContainer("$webUrl/api/v1/collections/$section?offset=$offset&limit=$limit")
    }

    fun getSeeMore(mainKeyword : String, urlToSkip: String = "", offset : Int = 0, limit : Int = 5) : List<ArticleContainer>  {
        val reqUrl = "$webUrl/api/v1/advanced-search/?offset=$offset&limit=$limit&sort=latest-published&tag-name=$mainKeyword"
        return extractContainer(reqUrl, urlToSkip)
    }

    private fun extractContainer (rawUrl: String, urlToSkip: String = "") : List<ArticleContainer> {

        val fieldsParam = "&fields=headline,subheadline,url,last-published-at,hero-image-s3-key,hero-image-metadata,last-published-at,alternative"
        val url = rawUrl + fieldsParam

        val doc = Jsoup.connect(url).ignoreContentType(true).execute()
        val respJson = JSONObject(doc.body())

        val articleContainers = mutableListOf<ArticleContainer>()

        val newsContainers = respJson.getJSONArray("items")


        for (i in 0 until newsContainers.length()) {

            val newsContainer = newsContainers.getJSONObject(i)

            val story = try {
                    if (urlToSkip.isEmpty())
                        newsContainer.getJSONObject("story")
                    else newsContainer
            }
            catch (e : org.json.JSONException) {
                val bug = rawUrl.substringBefore("?").substringAfter("-")
                val patch = rawUrl.replace(bug,"all")

                return extractContainer(patch, urlToSkip)

            }

            story.remove("linked-stories")

            val headline = story.getString("headline")
            val newsUrl = story.getString("url")

            if (urlToSkip.isNotEmpty() && newsUrl == urlToSkip) continue

            val date = story.getString("last-published-at").toLong()
            var imgSlug = story.getString("hero-image-s3-key")
            if (imgSlug == "null")
                imgSlug = Regex("\"hero-image-s3-key\"\\s*:\\s*\"([^\"]+)\"")
                    .find(story.toString())?.groupValues?.get(1)?.replace("\\", "").toString()

            val image = "https://media.prothomalo.com/$imgSlug"
            val subHeadline = story.optString("subheadline","").trim()

            articleContainers += ArticleContainer(headline, image, newsUrl, formatTimeAgo(date), subHeadline)

        }

        return articleContainers
    }

    fun getNews (newsUrlx : String) : NewsContainer {

        val newsUrl = newsUrlx.replace("%3A",":").replace("%2F","/")
        val parsedUrl = Uri.parse(newsUrl)
        val routeUrl = "https://${parsedUrl.host}/route-data.json?path=${parsedUrl.path}"

        val doc = Jsoup.connect(routeUrl).ignoreContentType(true).execute()

        val respJson = JSONObject(doc.body())
        val storyObject = respJson.getJSONObject("data").getJSONObject("story")
        val cardObjects = storyObject.getJSONArray("cards")


        val newsBody = mutableListOf<Any>()

        val imageHost = "https://media.prothomalo.com/"
        var coverImage = ""

        if (storyObject.getString("hero-image-s3-key") != "null")
            coverImage = imageHost + storyObject.getString("hero-image-s3-key")

        var coverImageCaption = storyObject.getString("hero-image-caption")
        val coverImageAttribution = storyObject.optString("image-attribution") ?: ""
        if (coverImageAttribution != "null" && coverImageAttribution.isNotEmpty())
            coverImageCaption += " | $coverImageAttribution"
        newsBody += Pair(coverImage, coverImageCaption)

        for (i in 0 until cardObjects.length()) {
            val cardObject = cardObjects.getJSONObject(i)
            val storyElements = cardObject.getJSONArray("story-elements")

            for (j in 0 until storyElements.length()) {
                val storyElement = storyElements.getJSONObject(j)
                if (storyElement.getString("type") == "text" && (storyElement.getString("subtype") == "null")) {
                    val text = storyElement.optString("text") ?: ""
                    newsBody += text
                }

                else if (storyElement.getString("type") == "image") {
                    val imgUrl = "https://media.prothomalo.com/" + storyElement
                        .getString("image-s3-key")
                    var caption = storyElement.optString("title") ?: ""
                    val imageAttribution = storyElement.optString("image-attribution") ?: ""
                    if (imageAttribution != "null" && imageAttribution.isNotEmpty())
                        caption += " | $imageAttribution"
                    newsBody += Pair(imgUrl, caption)
                }

            }
        }

        val headline = storyObject.optString("headline")
        val summary = storyObject.optString("summary")
        val sectionObj = storyObject.getJSONArray("sections").getJSONObject(0)
        val section = sectionObj.getString("name")
        val sectionSlug = sectionObj.getJSONObject("collection").getString("slug")
        val date = storyObject.getString("last-published-at").toLong()
        val author = storyObject.optString("author-name")
        val authorLocation = storyObject.optJSONObject("metadata")?.optString("author-location") ?: ""

        val mainKeyword = storyObject.getJSONObject("seo")
            .getJSONArray("meta-keywords").getString(0)

        return NewsContainer (
            headline,summary, author, authorLocation, formatTimeAgo(date), section,
            sectionSlug, newsBody, getSeeMore(mainKeyword, newsUrl), mainKeyword )


    }

}


