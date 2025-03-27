package com.ycngmn.prothomalo.scraper

class PaloEnglish : ProthomAlo() {

    override val webUrl = "https://en.prothomalo.com"

    override var articleSections = mapOf(
        "featured" to "Home",
        "latest" to "Latest",
        "exclusive" to "Exclusive",
        "discussed" to "আলোচিত",
        "special-news" to "বিশেষ সংবাদ",
        "goodnews" to "সুখবর",
        "fun" to "একটু থামুন",
        "onnoalo" to "অন্য আলো",
        "kishoralo-home-featured" to "কিশোর আলো",
        "bigganchinta-feature" to "বিজ্ঞানচিন্তা",
        "home-nagorik-sangbad" to "নাগরিক সংবাদ",
        "home-durporobash" to "দূর পরবাস",
    )
}