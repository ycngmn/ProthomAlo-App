package com.ycngmn.prothomalo.scraper.subs

import com.ycngmn.prothomalo.R
import com.ycngmn.prothomalo.scraper.ProthomAlo

class KishoreAlo : ProthomAlo() {

    override val webUrl = "https://www.kishoralo.com"

    override val dayLogo = R.drawable.kishore_alo_logo
    override val nightLogo = R.drawable.kishore_alo_night

    override var articleSections = mapOf(
        "kishoralo-home-featured" to "প্রচ্ছদ",
        "feature-kishoralo" to "ফিচার",
        "golpo-kishoralo" to "গল্প",
        "kobita-kishoralo" to "কবিতা",
        "comics-kishoralo" to "কমিকস",
        "kia-uponnas" to "উপন্যাস",
        "kia-chora" to "ছড়া",
        "kia-koutuk" to "কৌতুক",
        "entertainment-kishoralo" to "বিনোদন",
        "letter-kishoralo" to "চিঠিপত্র",
        "other-kishoralo" to "আরও",
    )

    override val menuMap:  Map<Pair<String, String>, List<Pair<String, String>>> = mapOf(
        Pair("গল্প", "golpo-kishoralo") to listOf(),
        Pair("কবিতা", "kobita-kishoralo") to listOf(),
        Pair("কমিকস", "comics-kishoralo") to listOf(),
        Pair("সাক্ষাৎকার", "interview-kishoralo") to listOf(),
        Pair("ফিচার", "feature-kishoralo") to listOf(),
        Pair("খেলা", "sports-kishoralo") to listOf(),
        Pair("বিনোদন", "entertainment-kishoralo") to listOf(),
        Pair("জীবনযাপন", "lifestyle-kishoralo") to listOf(),
        Pair("চিঠিপত্র", "letter-kishoralo") to listOf(),
        Pair("প্রশ্ন-উত্তর", "qa-kishoralo") to listOf()

    )
}