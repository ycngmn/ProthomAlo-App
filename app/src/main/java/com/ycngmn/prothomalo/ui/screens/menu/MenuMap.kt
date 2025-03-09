package com.ycngmn.prothomalo.ui.screens.menu

val sectionMap = mapOf(
    Pair("বাংলাদেশ", "bangladesh") to listOf(
        Pair("রাজধানী", "capitalcity-bangladesh"),
        Pair("জেলা", "district-bangladesh"),
        Pair("করোনাভাইরাস", "coronavirus"),
        Pair("পরিবেশ", "environment-bangladesh"),
        Pair("অপরাধ", "crime-bangladesh")
    ),
    Pair("বিশ্ব", "world") to listOf(
        Pair("ভারত", "india-international"),
        Pair("পাকিস্তান", "pakistan-international"),
        Pair("চীন", "china-international"),
        Pair("মধ্যপ্রাচ্য", "middle-east-world"),
        Pair("যুক্তরাষ্ট্র", "usa-international"),
        Pair("এশিয়া", "asia-international"),
        Pair("ইউরোপ", "europe-international"),
        Pair("আফ্রিকা", "africa-international"),
        Pair("লাতিন আমেরিকা", "south-america-international"),
        Pair("মার্কিন নির্বাচন", "us-election-all")
    ),
    Pair("মতামত", "opinion") to listOf(
        Pair("সম্পাদকীয়", "editorial-opinion"),
        Pair("কলাম", "column-opinion"),
        Pair("সাক্ষাৎকার", "interview-opinion"),
        Pair("স্মরণ", "memoir-opinion"),
        Pair("প্রতিক্রিয়া", "reaction-opinion"),
        Pair("চিঠি", "letter-opinion")
    ),
    Pair("বাণিজ্য", "business") to listOf(
        Pair("শেয়ারবাজার", "stockexchange"),
        Pair("ব্যাংক", "bank-economy"),
        Pair("শিল্প", "industry-economy"),
        Pair("অর্থনীতি", "economics-business"),
        Pair("বিশ্ববাণিজ্য", "world-economy-economy"),
        Pair("বিশ্লেষণ", "analysis-economy"),
        Pair("আপনার টাকা", "personal-finance-business"),
        Pair("উদ্যোক্তা", "entrepreneur"),
        Pair("করপোরেট সংবাদ", "corporate-economy"),
        Pair("বাজেট ২০২৪-২৫", "budget-2024-25")
    ),
    Pair("বিনোদন", "entertainment") to listOf(
        Pair("টেলিভিশন", "tv-entertainment"),
        Pair("ওটিটি", "ott-entertainment"),
        Pair("ঢালিউড", "dhaliwood-entertainment"),
        Pair("টালিউড", "tollywood-entertainment"),
        Pair("বলিউড", "bollywood-entertainment"),
        Pair("হলিউড", "hollywood-entertainment"),
        Pair("বিশ্ব চলচ্চিত্র", "world-cinema-entertainment"),
        Pair("গান", "song-entertainment"),
        Pair("নাটক", "stageperformance-entertainment"),
        Pair("আলাপন", "alapon-entertainment")
    ),
    Pair("জীবনযাপন", "lifestyle") to listOf(
        Pair("ভ্রমণ", "travel-life-style"),
        Pair("সম্পর্ক", "relation-life-style"),
        Pair("সুস্থতা", "health-life-style"),
        Pair("রাশি", "horoscope-life-style"),
        Pair("স্টাইল", "style-lifestyle"),
        Pair("রূপচর্চা", "beauty-lifestyle"),
        Pair("গৃহসজ্জা", "interior-lifestyle"),
        Pair("রসনা", "recipe-lifestyle"),
        Pair("কেনাকাটা", "shopping-lifestyle")
    ),
    Pair("চাকরি", "chakri") to listOf(
        Pair("খবর", "chakri-news-chakri"),
        Pair("নিয়োগ", "employment-chakri"),
        Pair("পরামর্শ", "chakri-suggestion-chakri"),
        Pair("সাক্ষাৎকার", "chakri-interview-chakri")
    ),
    Pair("খেলা", "sports") to listOf(
        Pair("ক্রিকেট", "cricket-sports"),
        Pair("ফুটবল", "football-sports"),
        Pair("টেনিস", "tennis-sports"),
        Pair("অন্য খেলা", "games-sports"),
        Pair("সাক্ষাৎকার", "interview-sports"),
        Pair("ফটো ফিচার", "sports-photo-feature"),
        Pair("কুইজ", "sports-quiz")
    ),
    Pair("প্রযুক্তি", "technology") to listOf(
        Pair("গ্যাজেট", "gadget-technology"),
        Pair("টিপস", "advice-technology"),
        Pair("বিজ্ঞান", "science-education"),
        Pair("অটোমোবাইল", "automobiles-science-tech-education"),
        Pair("সাইবার-জগৎ", "cyberworld-science-tech-education"),
        Pair("ফ্রিল্যান্সিং", "freelancing-technology"),
        Pair("এআই", "artificial-intelligence")
    ),
    Pair("শিক্ষা", "education") to listOf(
        Pair("ভর্তি", "admission-education"),
        Pair("পরীক্ষা", "examination-education"),
        Pair("বৃত্তি", "scholarship-education"),
        Pair("পড়াশোনা", "study-education"),
        Pair("উচ্চশিক্ষা", "higher-education"),
        Pair("ক্যাম্পাস", "campus-education")
    ),
    Pair("ধর্ম", "religion") to listOf(
        Pair("ইসলাম", "islam-all"),
        Pair("সনাতন", "hindu-religion"),
        Pair("বৌদ্ধ", "buddhist-religion"),
        Pair("খ্রিস্টান", "christian-religion")
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
        Pair("প্রতিষ্ঠাবার্ষিকী", "widget-anniversary-2024")
    )
)