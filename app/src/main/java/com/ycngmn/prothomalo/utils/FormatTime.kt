package com.ycngmn.prothomalo.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object FormatTime {

    private val isPaloEnglish : (url:  String) -> Boolean = { it.contains("//en.") }

    fun Long.toBengaliNumber(): String = this.toString()
        .replace('0', '০')
        .replace('1', '১')
        .replace('2', '২')
        .replace('3', '৩')
        .replace('4', '৪')
        .replace('5', '৫')
        .replace('6', '৬')
        .replace('7', '৭')
        .replace('8', '৮')
        .replace('9', '৯')

    fun toAgoString(milliseconds: Long, url: String): String {

        val diff = System.currentTimeMillis() - milliseconds

        val isPaloEnglish = isPaloEnglish(url)
        val minuteUnit = if (isPaloEnglish) "minute" else "মিনিট"
        val hourUnit = if (isPaloEnglish) "hour" else "ঘণ্টা"
        val dayUnit = if (isPaloEnglish) "day" else "দিন"

        val (value, unit) = when {
            diff < 3600000 -> diff / 60000 to minuteUnit
            diff < 86400000 -> diff / 3600000 to hourUnit
            diff < 259200000 -> diff / 86400000 to dayUnit
            else -> return toFormattedDate(milliseconds, url)
        }

        return if (isPaloEnglish) "$value ${unit}${if (value > 1) "s" else ""} ago"
        else "${value.toBengaliNumber()} $unit আগে"
    }

    fun toFormattedDate(milliseconds: Long, url: String): String {
        val locale = if (isPaloEnglish(url)) Locale.ENGLISH else Locale("bn","BD")
        return SimpleDateFormat("dd-MM-yyyy hh:mm", locale)
            .format(Date(milliseconds))
    }

}