package com.ycngmn.prothomalo.prothomalo.subs

import com.ycngmn.prothomalo.R
import com.ycngmn.prothomalo.prothomalo.ProthomAlo

class BondhuShava : ProthomAlo() {

    override val webUrl = "https://www.bondhushava.com"

    override val dayLogo = R.drawable.bondhushava_logo
    override val nightLogo = R.drawable.bondhushava_night

    override var homeSections = mapOf(
        "bondhushava-featured" to "প্রচ্ছদ",
        "writings-bondhushava" to "বন্ধুদের লেখা",
        "publications-bondhushava" to "প্রকাশনা",
        "activities-bondhushava" to "কার্যক্রম",
        "events-bondhushava" to "অনুষ্ঠান"
    )

    override val menuMap: Map<Pair<String, String>, List<Pair<String, String>>> =
        mapOf(
            Pair("প্রচ্ছদ", "bondhushava-featured") to emptyList(),
            Pair("কার্যক্রম", "activities-bondhushava") to emptyList(),
            Pair("অনুষ্ঠান", "events-bondhushava") to emptyList(),
            Pair("বন্ধুদের লেখা", "writings-bondhushava") to emptyList(),
            Pair("প্রকাশনা", "publications-bondhushava") to emptyList(),
        )

    override val menuMediaSection = mapOf(
        "ছবি" to "photo-bondhushava",
        "ভিডিও" to "video-all"
    )

}