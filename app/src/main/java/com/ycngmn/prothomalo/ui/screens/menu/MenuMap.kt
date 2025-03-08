package com.ycngmn.prothomalo.ui.screens.menu

val sectionMap = mapOf(
    Pair("বাংলাদেশ", "bangladesh") to listOf(
        Pair("রাজধানী", "capitalcity-bangladesh"),
        Pair("জেলা", "district-bangladesh"),
        Pair("করোনাভাইরাস", "coronavirus"),
        Pair("পরিবেশ", "bangladesh/environment"),
        Pair("অপরাধ", "crime-bangladesh")
    ),
    Pair("বিশ্ব", "world") to listOf(
        Pair("ভারত", "india-international"),
        Pair("পাকিস্তান", "pakistan"),
        Pair("চীন", "china"),
        Pair("মধ্যপ্রাচ্য", "middle-east"),
        Pair("যুক্তরাষ্ট্র", "usa-international"),
        Pair("এশিয়া", "asia-international"),
        Pair("ইউরোপ", "europe-international"),
        Pair("আফ্রিকা", "africa-international"),
        Pair("লাতিন আমেরিকা", "south-america-international"),
        Pair("মার্কিন নির্বাচন", "us-election")
    ),
    Pair("মতামত", "opinion") to listOf(
        Pair("সম্পাদকীয়", "editorial"),
        Pair("কলাম", "column"),
        Pair("সাক্ষাৎকার", "interview"),
        Pair("স্মরণ", "memoir"),
        Pair("প্রতিক্রিয়া", "reaction"),
        Pair("চিঠি", "letter")
    ),
    Pair("বাণিজ্য", "business") to listOf(
        Pair("শেয়ারবাজার", "market-business"),
        Pair("ব্যাংক", "bank"),
        Pair("শিল্প", "industry"),
        Pair("অর্থনীতি", "economics"),
        Pair("বিশ্ববাণিজ্য", "world-economy-economy"),
        Pair("বিশ্লেষণ", "analysis-economy"),
        Pair("আপনার টাকা", "personal-finance"),
        Pair("উদ্যোক্তা", "উদ্যোক্তা"),
        Pair("করপোরেট সংবাদ", "corporate-economy"),
        Pair("বাজেট ২০২৪-২৫", "budget-2024-25")
    ),
    Pair("বিনোদন", "entertainment") to listOf(
        Pair("টেলিভিশন", "tv-entertainment"),
        Pair("ওটিটি", "ott"),
        Pair("ঢালিউড", "dhaliwood-entertainment"),
        Pair("টালিউড", "tollywood"),
        Pair("বলিউড", "bollywood-entertainment"),
        Pair("হলিউড", "hollywood-entertainment"),
        Pair("বিশ্ব চলচ্চিত্র", "world-cinema"),
        Pair("গান", "song-entertainment"),
        Pair("নাটক", "stageperformance-entertainment"),
        Pair("আলাপন", "alapon-entertainment")
    ),
    Pair("জীবনযাপন", "lifestyle") to listOf(
        Pair("ভ্রমণ", "travel-life-style"),
        Pair("সম্পর্ক", "relation-life-style"),
        Pair("সুস্থতা", "health-life-style"),
        Pair("রাশি", "horoscope-life-style"),
        Pair("স্টাইল", "style"),
        Pair("রূপচর্চা", "beauty"),
        Pair("গৃহসজ্জা", "interior"),
        Pair("রসনা", "recipe"),
        Pair("কেনাকাটা", "shopping")
    ),
    Pair("চাকরি", "chakri") to listOf(
        Pair("খবর", "chakri-news"),
        Pair("নিয়োগ", "employment"),
        Pair("পরামর্শ", "chakri-suggestion"),
        Pair("সাক্ষাৎকার", "chakri-interview")
    ),
    Pair("খেলা", "sports") to listOf(
        Pair("ক্রিকেট", "cricket-sports"),
        Pair("ফুটবল", "football-sports"),
        Pair("টেনিস", "tennis"),
        Pair("অন্য খেলা", "other-sports"),
        Pair("সাক্ষাৎকার", "sports-interview"),
        Pair("ফটো ফিচার", "sports-photo-feature"),
        Pair("কুইজ", "sports-quiz")
    ),
    Pair("প্রযুক্তি", "technology") to listOf(
        Pair("গ্যাজেট", "gadget-technology"),
        Pair("টিপস", "advice-technology"),
        Pair("বিজ্ঞান", "science"),
        Pair("অটোমোবাইল", "automobiles"),
        Pair("সাইবার-জগৎ", "cyberworld"),
        Pair("ফ্রিল্যান্সিং", "freelancing-technology"),
        Pair("এআই", "artificial-intelligence")
    ),
    Pair("শিক্ষা", "education") to listOf(
        Pair("ভর্তি", "admission"),
        Pair("পরীক্ষা", "examination"),
        Pair("বৃত্তি", "scholarship"),
        Pair("পড়াশোনা", "study"),
        Pair("উচ্চশিক্ষা", "higher-education"),
        Pair("ক্যাম্পাস", "campus")
    ),
    Pair("ধর্ম", "religion") to listOf(
        Pair("ইসলাম", "islam"),
        Pair("সনাতন", "hindu"),
        Pair("বৌদ্ধ", "buddhist"),
        Pair("খ্রিস্টান", "christian")
    ),

    Pair("অন্য আলো", "onnoalo") to listOf(
        Pair("কবিতা", "poem-onnoalo"),
        Pair("গল্প", "stories-onnoalo"),
        Pair("নিবন্ধ", "treatise-onnoalo"),
        Pair("বইপত্র", "books-onnoalo"),
        Pair("শিল্পকলা", "arts-onnoalo"),
        Pair("সাক্ষাৎকার", "interview-onnoalo"),
        Pair("ভ্রমণ", "travel-onnoalo"),
        Pair("অন্যান্য", "others-onnoalo"),
        Pair("অনুবাদ", "translation-onnoalo"),
        Pair("মুক্ত গদ্য", "prose-onnoalo"),
        Pair("শিশু-কিশোর", "children-onnoalo")
    ),
    Pair("আরও", "") to listOf(
        Pair("গোলটেবিল", "roundtable"),
        Pair("বিশেষ সংখ্যা", "special-supplement"),
        Pair("প্রতিষ্ঠাবার্ষিকী", "anniversary")
    )
)