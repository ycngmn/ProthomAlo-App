package com.ycngmn.prothomalo.scraper.subs

import com.ycngmn.prothomalo.R
import com.ycngmn.prothomalo.scraper.ProthomAlo

class BondhuShava : ProthomAlo() {

    override val webUrl = "https://www.bondhushava.com"

    override val dayLogo = R.drawable.bondhushava_logo
    override val nightLogo = R.drawable.bondhushava_night

    override var articleSections = mapOf(
        "bondhushava-featured" to "প্রচ্ছদ",
        "writings-bondhushava" to "বন্ধুদের লেখা",
        "publications-bondhushava" to "প্রকাশনা",
        "activities-bondhushava" to "কার্যক্রম",
        "events-bondhushava" to "অনুষ্ঠান"
    )
}