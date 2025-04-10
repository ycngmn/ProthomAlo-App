package com.ycngmn.prothomalo.ui.assets

import com.ycngmn.prothomalo.prothomalo.PaloGlobal
import com.ycngmn.prothomalo.prothomalo.subs.PaloKeys

object Strings {
    private val en = mapOf(
        "home_tab" to "Home",
        "settings_tab" to "Settings",
        "bookmark_tab" to "Bookmark",
        "menu_tab" to "Menu",
        "read_more_title" to "More on <u>%s</u>",

        "theme" to "Theme",
        "theme_auto" to "Automatic",
        "theme_day" to "Day",
        "theme_night" to "Night",
        "theme_hint" to "Automatic, Day, Night",
        "confirm_theme" to "Apply",

        "font_size" to "Font Size",
        "font_size_hint" to "Small, Medium, Large",
        "read_more" to "Read More",
        "read_more_hint" to "Section at article bottom",

        "section_rearrange" to "Rearrange Home",
        "section_rearrange_hint" to "Top section tabs",

        "search_title" to "Search Results",
        "label_date" to "Date",
        "label_author" to "Author",
        "label_category" to "Category",
        "label_type" to "Type",
        "filter_hint" to "Search more specifically",
        "search_prompt" to "What do you want to search?",

        "type_text" to "Text",
        "type_photo" to "Photo",
        "type_video" to "Video",
        "type_live_blog" to "Live Blog",
        "type_interview" to "Interview",

        "empty_bookmark_1" to "To save a news item, go inside the news and tap on",
        "empty_bookmark_2" to "icon.",
        "empty_bookmark_button" to "Got it",
        "bookmark_save_toast" to "Article bookmarked",
        "bookmark_delete_toast" to "Bookmark removed",

        "share_article" to "Share article",
        "copied_to_clipboard" to "Copied to clipboard",
        "creating_pdf" to "Creating PDF...",
        "pdf_failed" to "Failed to create PDF",
        "copy_url" to "Copy URL",
        "share_as_pdf" to "Share as PDF",

        "error_connection_title" to "Connection Error",
        "error_connection_sub" to "Please check your internet connection.",
        "error_connection_button" to "Try Again",
        "error_404_title" to "No Results Found",
        "error_404_sub" to "The object you are looking for could not be found. This topic is probably not related to Prothom Alo or you are searching incorrectly. Please be sure about your search topic.",
        "error_404_button" to "Go Back"
    )

    private val bn = mapOf(
        "home_tab" to "প্রচ্ছদ",
        "settings_tab" to "বিন্যাস",
        "bookmark_tab" to "সংরক্ষণ",
        "menu_tab" to "তালিকা",
        "read_more_title" to "<u>%s</u> নিয়ে আরও পড়ুন",

        "theme" to "প্রদর্শন শৈলী",
        "theme_auto" to "স্বয়ংক্রিয়",
        "theme_day" to "দিবা",
        "theme_night" to "নিশা",
        "theme_hint" to "স্বয়ংক্রিয়, দিবা, নিশা",
        "confirm_theme" to "নিশ্চিত করুন",

        "font_size" to "অক্ষরের আকার",
        "font_size_hint" to "ছোট, মাঝারি, বড়",
        "read_more" to "আরও পড়ুন",
        "read_more_hint" to "খবরের নিচে প্রস্তাবিত অংশ",

        "section_rearrange" to "বিভাগ সজ্জা",
        "section_rearrange_hint" to "প্রচ্ছদ পাতায় বিভাগের পুনর্বিন্যাস",

        "search_title" to "অনুসন্ধান ফলাফল",
        "label_date" to "তারিখ",
        "label_author" to "লেখক",
        "label_category" to "বিভাগ",
        "label_type" to "ধরন",
        "filter_hint" to "আরও নির্দিষ্ট করে খুঁজুন",
        "search_prompt" to "যা খুঁজতে চান",

        "type_text" to "পাঠ্য",
        "type_photo" to "ছবি",
        "type_video" to "ভিডিও",
        "type_live_blog" to "সরাসরি",
        "type_interview" to "সাক্ষাৎকার",

        "empty_bookmark_1" to "কোন খবর সংরক্ষণ করতে খবরের ভিতরে প্রবেশ করে",
        "empty_bookmark_2" to "চিহ্নতে স্পর্শ করুন।",
        "empty_bookmark_button" to "বুঝেছি",
        "bookmark_save_toast" to "সংরক্ষণ সফল হয়েছে",
        "bookmark_delete_toast" to "নিবন্ধটি মুছে ফেলা হয়েছে",

        "share_article" to "লেখাটি ভাগ করুন",
        "copied_to_clipboard" to "অনুলিপন সফল",
        "creating_pdf" to "পিডিএফ তৈরি হচ্ছে...",
        "pdf_failed" to "পিডিএফ তৈরি ব্যর্থ হয়েছে",
        "copy_url" to "ঠিকানা অনুলিপি",
        "share_as_pdf" to "পিডিএফ ভাগ",

        "error_connection_title" to "সংযোগ ব্যর্থ হয়েছে",
        "error_connection_sub" to "",
        "error_connection_button" to "আবার চেষ্টা করুন",
        "error_404_title" to "কিছু পাওয়া যায়নি",
        "error_404_sub" to "আপনি যা খুঁজছেন, তা পাওয়া যায়নি। বিষয়টি হয়তো প্রথম আলোর অন্তর্ভুক্ত নয়, অথবা অনুসন্ধানে কোনো ভুল হয়েছে। দয়া করে তথ্যটি নিশ্চিত করে আবার চেষ্টা করুন।",
        "error_404_button" to "পিছনে ফিরে যান"
    )

    fun get(key: String, vararg args: Any): String {
        val value = when (PaloGlobal.paloKey) {
            PaloKeys.PaloEnglish ->  en[key]
            else -> bn[key]
        } ?: key
        return if (args.isNotEmpty()) String.format(value, *args) else value
    }
}