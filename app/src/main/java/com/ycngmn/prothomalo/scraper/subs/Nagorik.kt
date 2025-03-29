package com.ycngmn.prothomalo.scraper.subs

import com.ycngmn.prothomalo.R
import com.ycngmn.prothomalo.scraper.ProthomAlo

class Nagorik : ProthomAlo() {

    override val webUrl = "https://nagorik.prothomalo.com"

    override val dayLogo = R.drawable.nagorik_songbad_logo
    override val nightLogo = R.drawable.nagorik_songbad_logo

    override var articleSections = mapOf(
        "nagorik-featured" to "প্রচ্ছদ",
        "ayojon-nagorik-sangbad" to "আয়োজন",
        "durporobash-life-style" to "দূর পরবাস",
        "prosperity-nagorik-sangbad" to "সফলতা",
        "reader-nagorik-sangbad" to "পাঠক",
        "arts-nagorik-sangbad" to "সংস্কৃতি",
        "travel-nagorik-sangbad" to "ভ্রমণ"
    )
}