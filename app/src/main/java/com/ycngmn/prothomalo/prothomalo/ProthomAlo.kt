package com.ycngmn.prothomalo.prothomalo

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.core.net.toUri
import androidx.room.Entity
import androidx.room.PrimaryKey
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

@Entity(tableName = "bookmarks")
data class NewsContainer(
    val headline: String = "",
    val summary: String? = "",
    val newsUrl: String = "",
    val author: String? = "",
    val authorLocation: String = "",
    val date: String = "",
    val section: String = "",
    val sectionSlug: String = "",
    val body: List<Pair<String, String>> = emptyList(),
    val readAlso: List<ArticleContainer> = emptyList(),
    val readAlsoText: String = "",
    @PrimaryKey(autoGenerate = true) val newsId: Int = 0,
)


open class ProthomAlo {

    open val webUrl = "https://prothomalo.com"

    open var articleSections = mapOf(
        "featured" to "প্রচ্ছদ",
        "latest" to "সর্বশেষ",
        "mostread" to "সবচেয়ে পঠিত",
        "fun" to "একটু থামুন",
        "onnoalo" to "অন্য আলো",
        "kishoralo-home-featured" to "কিশোর আলো",
        "bigganchinta-feature" to "বিজ্ঞানচিন্তা",
        "home-nagorik-sangbad" to "নাগরিক সংবাদ",
        "home-durporobash" to "দূর পরবাস",
        "goodnews" to "সুখবর",
    )
    open val menuMap = paloBnMap
    open val menuMediaSection = mapOf(
        "ছবি" to "photo",
        "ভিডিও" to "video"
    )

    open val dayLogo = R.drawable.palo_bangla_logo
    open val nightLogo = R.drawable.palo_bangla_night

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

    private fun extractContainer (rawUrl: String, urlToSkip: String = "", isTryAgain: Boolean = false, isFieldsParam : Boolean = true) : List<ArticleContainer> {

        val fieldsParam = "&fields=headline,subheadline,url,last-published-at,hero-image-s3-key,hero-image-metadata,last-published-at,alternative"
        val url = if (isFieldsParam) rawUrl + fieldsParam else rawUrl

        val doc = Jsoup.connect(url).maxBodySize(0)
            .ignoreContentType(true).execute()

        val respJson = JSONObject(doc.body())

        val articleContainers = mutableListOf<ArticleContainer>()

        val newsContainers = respJson.getJSONArray("items")

        if (newsContainers.length() == 0 && !isTryAgain)
            return tryAgainExtract(rawUrl, urlToSkip)

        for (i in 0 until newsContainers.length()) {

            val newsContainer = newsContainers.getJSONObject(i)

            val story = try {
                    if (urlToSkip.isEmpty())
                        newsContainer.getJSONObject("story")
                    else newsContainer
            }
            catch (_ : org.json.JSONException) {
                return tryAgainExtract(rawUrl, urlToSkip)
            }

            val newsUrl = story.getString("url")
            if (urlToSkip.isNotEmpty() && newsUrl == urlToSkip) continue

            articleContainers += articleFromStory(story)
        }
        return articleContainers
    }

    private fun articleFromStory (story : JSONObject) : ArticleContainer {

        story.remove("linked-stories")

        val headline = story.getString("headline").replace("\n", "")
        val newsUrl = story.getString("url")

        val date = story.getString("last-published-at").toLong()
        var imgSlug = story.getString("hero-image-s3-key")
        if (imgSlug == "null")
            imgSlug = Regex("\"hero-image-s3-key\"\\s*:\\s*\"([^\"]+)\"")
                .find(story.toString())?.groupValues?.get(1)?.replace("\\", "").toString()

        val image = "https://media.prothomalo.com/$imgSlug"
        val subHeadline = story.optString("subheadline","")
            .replace("\n", "").trim()

        return ArticleContainer(headline.trim(), image, newsUrl, formatTimeAgo(date), subHeadline.trim())

    }

    private fun tryAgainExtract (rawUrl: String, urlToSkip: String = "") : List<ArticleContainer> {
        val bug = rawUrl.substringBefore("?")

        val patch = if (bug.contains("-"))
            rawUrl.replace(bug.substringAfter("-"),"all")
        else
            rawUrl.replace(bug.substringAfterLast("/"),"${bug.substringAfterLast("/")}-all")

        return extractContainer(patch, urlToSkip, isTryAgain = true )
    }

    fun getNews (newsUrlx : String) : NewsContainer {

        val newsUrl = newsUrlx.replace("%3A",":").replace("%2F","/")
        val parsedUrl = newsUrl.toUri()
        val routeUrl = "https://${parsedUrl.host}/route-data.json?path=${parsedUrl.path}"

        val doc = Jsoup.connect(routeUrl).ignoreContentType(true).execute()

        val respJson = JSONObject(doc.body())
        val storyObject = respJson.getJSONObject("data").getJSONObject("story")
        val cardObjects = storyObject.getJSONArray("cards")


        val newsBody = mutableListOf<Pair<String, String>>()

        val imageHost = "https://media.prothomalo.com/"
        var coverImage = ""

        if (!newsUrlx.contains("/video/")) {
            if (storyObject.getString("hero-image-s3-key") != "null")
                coverImage = imageHost + storyObject.getString("hero-image-s3-key")

            var coverImageCaption = storyObject.getString("hero-image-caption")
            val coverImageAttribution = storyObject.optString("image-attribution") ?: ""
            if (coverImageAttribution != "null" && coverImageAttribution.isNotEmpty())
                coverImageCaption += " | $coverImageAttribution"
            newsBody += Pair("image",coverImage)
            newsBody += Pair("caption",coverImageCaption)
        }

        for (i in 0 until cardObjects.length()) {
            val cardObject = cardObjects.getJSONObject(i)
            val storyElements = cardObject.getJSONArray("story-elements")

            for (j in 0 until storyElements.length()) {
                val storyElement = storyElements.getJSONObject(j)
                if (storyElement.getString("type") == "text" &&
                    ((storyElement.getString("subtype") == "null") || newsUrlx.contains("/video/"))) {
                    val text = storyElement.optString("text") ?: ""
                    newsBody += Pair("text",text)
                } else if (storyElement.getString("type") == "image") {
                    val imgUrl = "https://media.prothomalo.com/" + storyElement
                        .getString("image-s3-key")
                    var caption = storyElement.optString("title") ?: ""
                    val imageAttribution = storyElement.optString("image-attribution") ?: ""
                    if (imageAttribution != "null" && imageAttribution.isNotEmpty())
                        caption += " | $imageAttribution"
                    newsBody += Pair("image",imgUrl)
                    newsBody += Pair("caption",caption)
                } else if (storyElement.getString("type") == "youtube-video") {
                    val embedUrl = storyElement.getString("embed-url")
                    newsBody += Pair("video",embedUrl)
                }
            }
        }

        val headline = storyObject.optString("headline").replace("\n", "")
        val summary = storyObject.optString("summary")
        val sectionObj = storyObject.getJSONArray("sections").getJSONObject(0)
        val section = sectionObj.getString("name")
        val sectionSlug = sectionObj.getJSONObject("collection").getString("slug")
        val date = storyObject.getString("last-published-at").toLong()
        val author = storyObject.optString("author-name")
        val authorLocation = storyObject.optJSONObject("metadata")?.optString("author-location") ?: ""

        var mainKeyword: String
        var moreArticles: List<ArticleContainer>

        try {
            mainKeyword = storyObject.getJSONObject("seo")
                .getJSONArray("meta-keywords").getString(0)
            moreArticles = getSeeMore(mainKeyword, newsUrl)
        } catch (_: Exception) {
            mainKeyword = ""
            moreArticles = emptyList()
        }

        return NewsContainer (
            headline.trim(),summary.trim(), newsUrl, author, authorLocation, formatTimeAgo(date), section.trim(),
            sectionSlug, newsBody, moreArticles, mainKeyword )
    }

    private fun extractSearch(rawUrl: String): List<ArticleContainer> {
        val fieldsParam = "&fields=headline,subheadline,url,last-published-at,hero-image-s3-key,hero-image-metadata,last-published-at,alternative"
        val url =  rawUrl + fieldsParam

        val doc = Jsoup.connect(url).maxBodySize(0)
            .ignoreContentType(true).execute()

        val respJson = JSONObject(doc.body())

        val articleContainers = mutableListOf<ArticleContainer>()

        val stories = respJson.getJSONObject("data")
            .getJSONArray("stories")

        for (i in 0 until stories.length()) {
            val story = stories.getJSONObject(i)
            articleContainers += articleFromStory(story)
        }
        return articleContainers
    }

    fun search(query: String, author: String,
        sections: List<String>,
        types: List<String>, offset : Int = 0,
        limit : Int = 15): List<ArticleContainer> {

        var searchUrl = "$webUrl/route-data.json?path=search&offset=$offset&limit=$limit"
        if (query.isNotEmpty()) {
            searchUrl += "&q=$query"
        }
        if (author.isNotEmpty()) {
            searchUrl += "&author=$author"
        }
        if (sections.isNotEmpty()) {
            searchUrl += "&section=${sections.joinToString(",")}"
        }
        if (types.isNotEmpty()) {
            searchUrl += "&type=${types.joinToString(",")}"
        }
        return extractSearch(searchUrl)
    }

}