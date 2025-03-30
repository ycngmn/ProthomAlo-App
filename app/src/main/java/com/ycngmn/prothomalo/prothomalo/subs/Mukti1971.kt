package com.ycngmn.prothomalo.prothomalo.subs

import com.ycngmn.prothomalo.R
import com.ycngmn.prothomalo.prothomalo.ProthomAlo

class Mukti1971 : ProthomAlo() {

    override val webUrl = "https://1971.prothomalo.com"

    override val dayLogo = R.drawable.muktijuddho_logo
    override val nightLogo = R.drawable.muktijuddho_logo

    override var articleSections = mapOf(
        "special-article-muktijuddho-50" to "মুক্তিযুদ্ধ",
        "diary-1971-muktijuddho-50" to "মুক্তিযুদ্ধের দিনলিপি",
        "supplements-muktijuddho-50" to "ক্রোড়পত্র",
        "news-muktijuddho-50" to "খবর",
        "background-muktijuddho-50" to "পটভূমি",
        "rural-bangla-muktijuddho-50" to "জনপদের যুদ্ধ",
        "martyred-intellectuals-muktijuddho-50" to "শহীদ বুদ্ধিজীবী"
    )

    override val menuMap: Map<Pair<String, String>, List<Pair<String, String>>> =
     mapOf(
        Pair("পটভূমি", "background-muktijuddho-50") to listOf(
            Pair("ভাষা আন্দোলন", "language-movement-1947-1952-background-muktijuddho-50"),
            Pair("৫৪ এর নির্বাচন", "1954-election-background-muktijuddho-50"),
            Pair("৬২ এর ছাত্র আন্দোলন", "1962-student-movement-background-muktijuddho-50"),
            Pair("ছয় দফা আন্দোলন", "six-point-movement-background-muktijuddho-50"),
            Pair("আগরতলা-ষড়যন্ত্র-মামলা", "agartala-conspiracy-case-background-muktijuddho-50"),
            Pair("৬৯ এর গণ অভ্যুতথান", "mass-uprising-of-1969-background-muktijuddho-50"),
            Pair("৭০ এর নির্বাচন", "election-of-1970-background-muktijuddho-50"),
            Pair("অসহযোগ আন্দোলন মার্চ'৭১", "non-cooperation-movement-march-71-background-muktijuddho-50")
        ),
        Pair("মুজিবনগর সরকার", "mujibnagar-government-muktijuddho-50") to listOf(
            Pair("মন্ত্রিপরিষদ", "cabinet-mujibnagar-government-muktijuddho-50"),
            Pair("কর্মকর্তা", "officials-mujibnagar-government-muktijuddho-50"),
            Pair("সভার সিদ্ধান্ত", "meeting-decisions-mujibnagar-government-muktijuddho-50"),
            Pair("ছবি", "photo-mujibnagar-government-muktijuddho-50")
        ),
        Pair("সেক্টর, ফোর্স ও বাহিনী", "sector-force-muktijuddho-50") to listOf(
            Pair("সেক্টর", "sector-sector-force-muktijuddho-50"),
            Pair("ফোর্স", "force-sector-force-muktijuddho-50"),
            Pair("নৌবাহিনী", "navy-sector-force-muktijuddho-50"),
            Pair("বিমানবাহিনী", "air-force-sector-force-muktijuddho-50"),
            Pair("কাদেরিয়া বাহিনী", "kaderia-bahini-sector-force-muktijuddho-50"),
            Pair("মুজিব বাহিনী", "mujib-bahini-sector-force-muktijuddho-50"),
            Pair("অন্যান্য বাহিনী", "others-bahini-sector-force-muktijuddho-50")
        ),
        Pair("মুক্তিযোদ্ধাদের তথ্য", "freedom-fighters-info-muktijuddho-50") to listOf(
            Pair("বীরত্বসূচক খেতাবপ্রাপ্তদের তালিকা", "gallantry-awardees-freedom-fighters-info-muktijuddho-50"),
            Pair("মুক্তিযোদ্ধাদের তালিকা", "freedomfighters-list-freedom-fighters-info-muktijuddho-50"),

        ),
        Pair("ক্রোড়পত্র", "supplements-muktijuddho-50") to listOf(
            Pair("স্বাধীনতার আহবান-৭ মার্চ", "call-for-freedom-special-days-muktijuddho-50"),
            Pair("জনপদের যুদ্ধ", "rural-bangla-muktijuddho-50"),
            Pair("স্বাধীনতা দিবস-২৬ মার্চ", "independence-day-supplements-muktijuddho-50"),
            Pair("মুক্তিযুদ্ধের সরকার-১৭ এপ্রিল", "government-of-liberation-war-special-days-muktijuddho-50"),
            Pair("স্বাধীন বাংলা বেতার-২৫ মে", "independent-bangla-radio-special-days-muktijuddho-50"),
            Pair("অপারেশন জ্যাকপট-১৬ আগস্ট", "operation-jackpot-supplements-muktijuddho-50"),
            Pair("কিলো ফ্লাইট-২৮ সেপ্টেম্বর", "kilo-flight-supplements-muktijuddho-50"),
            Pair("বিজয় দিবস-১৬ ডিসেম্বর", "victory-day-supplements-muktijuddho-50"),
            Pair("অন্য আলো", "onno-alo-supplements-muktijuddho-50"),
            Pair("সাহিত্য সাময়িকী", "literature-supplements-muktijuddho-5")
        ),
         Pair("শহীদ বুদ্ধিজীবী", "martyred-intellectuals-muktijuddho-50") to listOf(),

         Pair("সাক্ষাৎকার", "interview-muktijuddho-50") to listOf(
            Pair("মুক্তিযোদ্ধা", "freedom-fighter-interview-muktijuddho-50"),
            Pair("প্রত্যক্ষদর্শী", "eyewitness-interview-muktijuddho-50"),
            Pair("সংগঠক", "organizer-interview-muktijuddho-50"),
            Pair("ক্ষতিগ্রস্থ", "affected-interview-muktijuddho-50"),
            Pair("গবেষক", "researcher-interview-muktijuddho-50"),
            Pair("বিশেষজ্ঞ", "specialist-interview-muktijuddho-50"),
            Pair("মিত্রপক্ষ", "allies-interview-muktijuddho-50"),
            Pair("শত্রুপক্ষ", "enemy-interview-muktijuddho-50")
        ),
        Pair("গণমাধ্যম", "media-muktijuddho-50") to listOf(
            Pair("বেতার", "radio-media-muktijuddho-50"),
            Pair("টেলিভিশন", "television-media-muktijuddho-50"),
            Pair("সংবাদ সংস্থা", "news-agency-media-muktijuddho-50"),
            Pair("জয়বাংলা", "joybangla-media-muktijuddho-50"),
            Pair("আনন্দবাজার পত্রিকা", "anandabazar-potrika-media-muktijuddho-50"),
            Pair("MORNING NEWS", "morning-news-media-muktijuddho-50"),
            Pair("THE PAKISTAN OBSERVER", "the-pakistan-observer-media-muktijuddho-50")
        ),
        Pair("নথি", "documents-muktijuddho-50") to listOf(
            Pair("বাংলাদেশ", "bangladesh-documents-muktijuddho-50"),
            Pair("ভারত", "india-documents-muktijuddho-50"),
            Pair("যুক্তরাষ্ট্র", "united-states-documents-muktijuddho-50"),
            Pair("অন্যান্য", "others-documents-muktijuddho-50")
        ),

        Pair("ভিডিও", "video-muktijuddho-50") to listOf(
            Pair("চলচ্চিত্র", "cinema-video-muktijuddho-50"),
            Pair("তথ্যচিত্র", "documentaries-video-muktijuddho-50"),
            Pair("সংবাদ", "news-video-muktijuddho-50"),
            Pair("বাংলাদেশী অভিজ্ঞতা", "bangladeshi-experience-video-muktijuddho-50"),
            Pair("ভারতীয় অভিজ্ঞতা", "indian-experience-video-muktijuddho-50"),
            Pair("বিবিধ", "others-video-muktijuddho-50")
        ),
        Pair("অডিও", "audio-muktijuddho-50") to listOf(
            Pair("সাক্ষাৎকার", "interview-audio-muktijuddho-50"),
            Pair("সংবাদ", "news-audio-muktijuddho-50"),
            Pair("বিবিধ", "others-audio-muktijuddho-50")
        ),
        Pair("খবর", "news-muktijuddho-50") to listOf(),
        Pair("বইপত্র", "books-1971-muktijuddho-50") to listOf(
            Pair("একাত্তরের চিঠি", "ekattorer-chithi-muktijuddho-50")),
        Pair("জন উদ্যোগ", "private-initiative-muktijuddho-50") to listOf(),
        Pair("শরণার্থী", "refugees-muktijuddho-50") to listOf(),
        Pair("গণহত্যা", "genocide-muktijuddho-50") to listOf(),
        Pair("যুদ্ধাপরাধ", "war-crimes-muktijuddho-50") to listOf(
            Pair("অপরাধ", "crime-war-crimes-muktijuddho-50"),
            Pair("বিচার", "judgement-war-crimes-muktijuddho-50")
        )
    )

    override val menuMediaSection = mapOf(
        "ছবি" to "photo-muktijuddho-50",
        "ভিডিও" to "video-muktijuddho-50"
    )


}