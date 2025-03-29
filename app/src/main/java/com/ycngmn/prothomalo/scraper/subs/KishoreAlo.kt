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
}