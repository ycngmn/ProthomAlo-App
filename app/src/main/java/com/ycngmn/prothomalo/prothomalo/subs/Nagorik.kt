package com.ycngmn.prothomalo.prothomalo.subs

import com.ycngmn.prothomalo.R
import com.ycngmn.prothomalo.prothomalo.ProthomAlo

class Nagorik : ProthomAlo() {

    override val webUrl = "https://nagorik.prothomalo.com"

    override val dayLogo = R.drawable.nagorik_songbad_logo
    override val nightLogo = R.drawable.nagorik_songbad_logo

    override var homeSections = mapOf(
        "nagorik-featured" to "প্রচ্ছদ",
        "ayojon-nagorik-sangbad" to "আয়োজন",
        "durporobash-life-style" to "দূর পরবাস",
        "prosperity-nagorik-sangbad" to "সফলতা",
        "reader-nagorik-sangbad" to "পাঠক",
        "arts-nagorik-sangbad" to "সংস্কৃতি",
        "travel-nagorik-sangbad" to "ভ্রমণ"
    )

    override val menuMap: Map<Pair<String, String>, List<Pair<String, String>>> =
        mapOf(
            Pair("প্রচ্ছদ", "nagorik-featured") to emptyList(),
            Pair("আয়োজন", "ayojon-nagorik-sangbad") to emptyList(),
            Pair("দূর পরবাস", "durporobash-life-style") to emptyList(),
            Pair("সফলতা", "prosperity-nagorik-sangbad") to emptyList(),
            Pair("পাঠক", "reader-nagorik-sangbad") to emptyList(),
            Pair("সংস্কৃতি", "arts-nagorik-sangbad") to emptyList(),
            Pair("ভ্রমণ", "travel-nagorik-sangbad") to emptyList(),
        )
    override val menuMediaSection = mapOf(
        "ছবি" to "photo-nagorik-sangbad",
        "ভিডিও" to "video-all"
    )

}