package com.ycngmn.prothomalo.scraper.subs

import com.ycngmn.prothomalo.R
import com.ycngmn.prothomalo.scraper.ProthomAlo

class BigganChinta : ProthomAlo() {

    override val webUrl = "https://www.bigganchinta.com"

    override val dayLogo = R.drawable.biggan_chinta_logo
    override val nightLogo = R.drawable.biggan_chinta_logo

    override var articleSections = mapOf(
        "bigganchinta-feature" to "প্রচ্ছদ",
        "features-bigganchinta" to "ফিচার",
        "bigganchinta-latest" to "সর্বশেষ",
        "physics-bigganchinta" to "পদার্থবিজ্ঞান",
        "technology-bigganchinta" to "প্রযুক্তি",
        "math-bigganchinta" to "গণিত",
        "space-bigganchinta" to "মহাকাশ",
        "biology-bigganchinta" to "জীববিজ্ঞান",
        "cause-bigganchinta" to "কার্যকারণ",
        "comics-bigganchinta" to "কমিকস",
        "rommo-bigganchinta" to "বিজ্ঞানরম্য"

    )
}