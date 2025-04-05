package com.ycngmn.prothomalo.prothomalo.subs

import com.ycngmn.prothomalo.R
import com.ycngmn.prothomalo.prothomalo.ProthomAlo

class PaloEnglish : ProthomAlo() {

    override val webUrl = "https://en.prothomalo.com"

    override val dayLogo = R.drawable.english_site_og_image
    override val nightLogo = R.drawable.palo_eng_night

    override var articleSections = mapOf(
        "latest" to "Home",
        "trending" to "Trending",
        "exclusive" to "Exclusive",
        "claim-vs-fact" to "Claim vs Fact",
        "bangladesh-in-world-media-bangladesh" to "Bangladesh in World Media",
        "good-day-bangladesh-bangladesh" to "Good Day Bangladesh"

    )

    override val menuMediaSection = mapOf(
        "Photo" to "photo",
        "Video" to "video"
    )

    override val menuMap: Map<Pair<String, String>, List<Pair<String, String>>> =
        mapOf(
            Pair("Bangladesh", "bangladesh") to listOf(
                Pair("Politics", "politics-bangladesh"),
                Pair("Accident", "accident-bangladesh"),
                Pair("Good Day Bangladesh", "good-day-bangladesh-bangladesh"),
                Pair("Crime", "crime-and-law-bangladesh"),
                Pair("Government", "government-bangladesh"),
                Pair("City", "city-bangladesh"),
                Pair("Local News", "local-news-bangladesh"),
                Pair("Parliament", "parliament-bangladesh"),
                Pair("Bangladesh in World Media", "bangladesh-in-world-media-bangladesh"),
                Pair("Roundtable", "roundtable-bangladesh")
            ),
            Pair("International", "international") to listOf(
                Pair("Asia", "asia-international"),
                Pair("Europe", "europe-international"),
                Pair("Americas", "americas-international"),
                Pair("Middle East", "middle-east-international"),
                Pair("India", "india"),
                Pair("China", "china-international"),
                Pair("Africa", "africa-international"),
                Pair("Australia", "australia-international"),
                Pair("South Asia", "south-asia-international")
            ),
            Pair("Sports", "sports") to listOf(
                Pair("Cricket", "cricket-sports"),
                Pair("Football", "football-sports"),
                Pair("Local Sports", "local-sports-sports"),
                Pair("ICC T20 World Cup", "icc-t20-worldcup")
            ),
            Pair("Opinion", "opinion") to listOf(
                Pair("Editorial", "editorial-opinion"),
                Pair("Interview", "interview-opinion"),
                Pair("Op-Ed", "op-ed-opinion")
            ),
            Pair("Business", "business") to listOf(
                Pair("Local", "local-business"),
                Pair("Global", "global-business")
            ),
            Pair("Youth", "youth") to listOf(
                Pair("Education", "education-youth"),
                Pair("Employment", "employment-youth")
            ),
            Pair("Entertainment", "entertainment") to listOf(
                Pair("Music", "music-entertainment"),
                Pair("Movies", "movies-entertainment"),
                Pair("Television", "television-entertainment"),
                Pair("OTT", "ott-entertainment")
            ),
            Pair("Lifestyle", "lifestyle") to listOf(
                Pair("Fashion", "fashion-lifestyle"),
                Pair("Well-being", "health-lifestyle"),
                Pair("Hair & Skin", "beauty-lifestyle"),
                Pair("Travel", "travel-lifestyle")
            ),
            Pair("Environment", "environment") to listOf(
                Pair("Climate", "climate-change-environment"),
                Pair("Pollution", "pollution-environment")
            ),
            Pair("Science & Technology", "science-technology") to listOf(
                Pair("Gadgets & Apps", "gadgets-science-technology"),
                Pair("Social Media", "social-media-science-technology"),
                Pair("IT", "it-science-technology"),
                Pair("Science", "science-science-technology")
            ),
            Pair("Corporate", "corporate") to listOf(
                Pair("Local", "local-corporate"),
                Pair("Global", "global-corporate")
            )
        )

}