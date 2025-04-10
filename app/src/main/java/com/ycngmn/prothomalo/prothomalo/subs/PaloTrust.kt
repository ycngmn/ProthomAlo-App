package com.ycngmn.prothomalo.prothomalo.subs

import com.ycngmn.prothomalo.R
import com.ycngmn.prothomalo.prothomalo.ProthomAlo

class PaloTrust : ProthomAlo() {

    override val webUrl = "https://trust.prothomalo.com"

    override val dayLogo = R.drawable.prothomalo_trust_logo
    override val nightLogo = R.drawable.palo_trust_night

    override var homeSections = mapOf(
        "trust-featured" to "প্রচ্ছদ",
        "alor-pathshala" to "আলোর পাঠশালা",
        "aparajeyo-tara" to "অপরাজেয় তারা",
        "oditiya" to "অদ্বিতীয়া",
        "anti-narcotics-andolon" to "মাদকবিরোধী আন্দোলন",
        "odommo-medhabi-tohbil" to "অদম্য মেধাবী তহবিল"
    )

    override val menuMap: Map<Pair<String, String>, List<Pair<String, String>>> =
        mapOf(
            Pair("প্রচ্ছদ", "trust-featured") to listOf(),
            Pair("কার্যক্রম", "activities") to listOf(
                Pair("অদম্য মেধাবী তহবিল", "odommo-medhabi-tohbil"),
                Pair("অপরাজেয় তারা", "aparajeyo-tara"),
                Pair("অদ্বিতীয়া", "oditiya"),
                Pair("আলোর পাঠশালা", "alor-pathshala"),
                Pair("মাদকবিরোধী আন্দোলন", "anti-narcotics-andolon"),
                Pair("ত্রাণ তহবিল", "tran-tohobil"),
                Pair("অ্যাসিডদগ্ধ নারীদের জন্য সহায়ক তহবিল", "acid-trust"),
                Pair("সাভার সহায়তা তহবিল", "savar-sohayota"),
                Pair("সাদত স্মৃতি পল্লী", "sadot-smriti-polli")
            )
        )

    override val menuMediaSection = mapOf(
        "ছবি" to "photo-trust",
        "ভিডিও" to "video-trust"
    )
}