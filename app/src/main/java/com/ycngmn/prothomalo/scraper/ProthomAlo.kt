package com.ycngmn.prothomalo.scraper

import android.util.Log
import org.json.JSONObject
import org.jsoup.Jsoup
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class ArticleContainer (
    val title: String = "",
    val image: String = "",
    val url: String = "",
    val date: String = "",
    val subHead: String = ""
)


class ProthomAlo {

    private val webUrl = "https://www.prothomalo.com/"

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

    fun getArticle(url: String) : List<ArticleContainer> {

        Log.d("TAG", "getArticle: $url")

        val doc = Jsoup.connect(url)
            .ignoreContentType(true).execute()

        val respJson = JSONObject(doc.body())

        val articleContainers = mutableListOf<ArticleContainer>()

        val newsContainers = respJson.getJSONArray("items")


        for (i in 0 until newsContainers.length()) {

            //Log.d("duck", "getArticle: ${newsContainers.length()}")
            val newsContainer = newsContainers.getJSONObject(i)
            val story = newsContainer.getJSONObject("story")

            val headline = story.getString("headline")

            val newsUrl = story.getString("url")
            val date = story.getString("last-published-at").toLong()
            val image = "https://media.prothomalo.com/" +
                    (Regex("\"hero-image-s3-key\"\\s*:\\s*\"([^\"]+)\"")
                        .find(story.toString())?.groupValues?.get(1))?.replace("\\", "")
            val subHeadline = story.optString("subheadline","").trim()


            articleContainers += ArticleContainer(headline, image, newsUrl, formatTimeAgo(date), subHeadline)


        }

        return articleContainers

    }
}

//fun main() {
//    println(ProthomAlo()
//        .getArticle("https://www.prothomalo.com/api/v1/collections/entertainment-all?offset=0&limit=10"))
//}


