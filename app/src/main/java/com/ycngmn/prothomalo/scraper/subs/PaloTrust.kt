package com.ycngmn.prothomalo.scraper.subs

import com.ycngmn.prothomalo.R
import com.ycngmn.prothomalo.scraper.ProthomAlo

class PaloTrust : ProthomAlo() {

    override val webUrl = "https://trust.prothomalo.com"

    override val dayLogo = R.drawable.prothomalo_trust_logo
    override val nightLogo = R.drawable.palo_trust_night

    override var articleSections = mapOf(
        "trust-featured" to "প্রচ্ছদ",
        "activities-trust" to "কার্যক্রম",
        "drug-abuse-activities-trust" to "মাদকবিরোধী আন্দোলন",
        "alor-school-activities-trust" to "আলোর পাঠশালা",
        "aparajeyo-tara" to "অপরাজেয় তারা",
        "oditiya" to "অদ্বিতীয়া",
        "odommo-medhabi-tohbil" to "অদম্য মেধাবী তহবিল"
    )
}