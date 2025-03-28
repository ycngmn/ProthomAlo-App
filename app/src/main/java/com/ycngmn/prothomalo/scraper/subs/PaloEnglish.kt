package com.ycngmn.prothomalo.scraper.subs

import com.ycngmn.prothomalo.R
import com.ycngmn.prothomalo.scraper.ProthomAlo

class PaloEnglish : ProthomAlo() {

    override val webUrl = "https://en.prothomalo.com"

    override val dayLogo = R.drawable.english_site_og_image
    override val nightLogo = R.drawable.palo_eng_night

    override var articleSections = mapOf(
        "featured" to "Home",
        "latest" to "Latest",
        "trending" to "Trending",
        "exclusive" to "Exclusive",
        "claim-vs-fact" to "Claim vs Fact",
        "bangladesh-in-world-media-bangladesh" to "Bangladesh in World Media",
        "good-day-bangladesh-bangladesh" to "Good Day Bangladesh"

    )
}