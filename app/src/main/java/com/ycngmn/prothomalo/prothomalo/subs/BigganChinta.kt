package com.ycngmn.prothomalo.prothomalo.subs

import com.ycngmn.prothomalo.R
import com.ycngmn.prothomalo.prothomalo.ProthomAlo

class BigganChinta : ProthomAlo() {

    override val webUrl = "https://www.bigganchinta.com"

    override val dayLogo = R.drawable.biggan_chinta_logo
    override val nightLogo = R.drawable.biggan_chinta_logo

    override var homeSections = mapOf(
        "bigganchinta-latest" to "প্রচ্ছদ",
        "features-bigganchinta" to "ফিচার",
        "physics-bigganchinta" to "পদার্থবিজ্ঞান",
        "technology-bigganchinta" to "প্রযুক্তি",
        "math-bigganchinta" to "গণিত",
        "space-bigganchinta" to "মহাকাশ",
        "biology-bigganchinta" to "জীববিজ্ঞান",
        "cause-bigganchinta" to "কার্যকারণ",
        "comics-bigganchinta" to "কমিকস",
        "rommo-bigganchinta" to "বিজ্ঞানরম্য"

    )

    override val menuMap: Map<Pair<String, String>, List<Pair<String, String>>> =
        mapOf(
            Pair("সর্বশেষ", "bigganchinta-latest") to listOf(),
            Pair("গেমস", "topic_গেমস-বিজ্ঞানচিন্তা") to listOf(),
            Pair("পদার্থবিজ্ঞান", "physics-bigganchinta") to listOf(),
            Pair("প্রযুক্তি", "technology-bigganchinta") to listOf(),
            Pair("গণিত", "math-bigganchinta") to listOf(),
            Pair("মহাকাশ", "space-bigganchinta") to listOf(),
            Pair("জীববিজ্ঞান", "biology-bigganchinta") to listOf(),
            Pair("কার্যকারণ", "cause-bigganchinta") to listOf(),
            Pair("বিজ্ঞান কল্পগল্প", "sciencefiction-bigganchinta") to listOf(),
            Pair("কমিকস", "comics-bigganchinta") to listOf(),
            Pair("বিজ্ঞানরম্য", "rommo-bigganchinta") to listOf(),
            Pair("সাক্ষাৎকার", "topic_সাক্ষাৎকার বিজ্ঞানচিন্তা") to listOf(),
            Pair("বিজ্ঞানী", "topic_বিজ্ঞানী বিজ্ঞানচিন্তা") to listOf(),
            Pair("জানা অজানা", "topic_জানা অজানা বিজ্ঞানচিন্তা") to listOf(),
            Pair("তথ্যকণা", "topic_তথ্যকণা বিজ্ঞানচিন্তা") to listOf(),
            Pair("আপডেট", "topic_আপডেট বিজ্ঞানচিন্তা") to listOf(),
            Pair("রসায়ন", "chemistry-bigganchinta") to listOf(),
            Pair("ইভেন্ট", "events-bigganchinta") to listOf(),
            Pair("পাঠকের লেখা", "topic_পাঠকের লেখা বিজ্ঞানচিন্তা") to listOf(),
            Pair("বিজ্ঞান উৎসব", "topic_বিজ্ঞান উৎসব") to listOf(),
            Pair("নোবেল পুরস্কার", "topic_নোবেল পুরস্কার-বিজ্ঞানচিন্তা") to listOf(),

        )
}