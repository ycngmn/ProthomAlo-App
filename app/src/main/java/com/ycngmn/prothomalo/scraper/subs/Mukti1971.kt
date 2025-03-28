package com.ycngmn.prothomalo.scraper.subs

import com.ycngmn.prothomalo.R
import com.ycngmn.prothomalo.scraper.ProthomAlo

class Mukti1971 : ProthomAlo() {

    override val webUrl = "https://1971.prothomalo.com"

    override val dayLogo = R.drawable.muktijuddho_logo
    override val nightLogo = R.drawable.muktijuddho_logo

    override var articleSections = mapOf(
        "special-article-muktijuddho-50" to "মুক্তিযুদ্ধ",
        "diary-1971-muktijuddho-50" to "মুক্তিযুদ্ধের দিনলিপি",
        "supplements-muktijuddho-50" to "ক্রোড়পত্র",
        "news-muktijuddho-50" to "খবর",
        "background-muktijuddho-50" to "পটভূমি",
        "rural-bangla-muktijuddho-50" to "জনপদের যুদ্ধ",
        "martyred-intellectuals-muktijuddho-50" to "শহীদ বুদ্ধিজীবী"
    )
}