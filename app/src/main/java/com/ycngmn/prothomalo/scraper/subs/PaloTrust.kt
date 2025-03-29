package com.ycngmn.prothomalo.scraper.subs

import com.ycngmn.prothomalo.R
import com.ycngmn.prothomalo.scraper.ProthomAlo

class PaloTrust : ProthomAlo() {

    override val webUrl = "https://trust.prothomalo.com"

    override val dayLogo = R.drawable.prothomalo_trust_logo
    override val nightLogo = R.drawable.palo_trust_night

    override var articleSections = mapOf(
        "trust-featured" to "প্রচ্ছদ",
        "alor-pathshala" to "আলোর পাঠশালা",
        "aparajeyo-tara" to "অপরাজেয় তারা",
        "oditiya" to "অদ্বিতীয়া",
        "anti-narcotics-andolon" to "মাদকবিরোধী আন্দোলন",
        "odommo-medhabi-tohbil" to "অদম্য মেধাবী তহবিল"
    )
}