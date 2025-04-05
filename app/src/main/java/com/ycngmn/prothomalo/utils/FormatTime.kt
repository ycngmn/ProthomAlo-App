package com.ycngmn.prothomalo.utils

import com.ycngmn.prothomalo.Strings
import com.ycngmn.prothomalo.prothomalo.PaloGlobal
import com.ycngmn.prothomalo.prothomalo.subs.PaloKeys
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object FormatTime {

    private val isPaloEnglish : () -> Boolean = { PaloGlobal.paloKey == PaloKeys.PaloEnglish }
    private val locale = if (isPaloEnglish()) Locale.ENGLISH else Locale("bn","BD")

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

    fun toAgoString(milliseconds: Long): String {

        val diff = System.currentTimeMillis() - milliseconds

        val (value, unit) = when {
            diff < 3600000 -> diff / 60000 to Strings.get("minute_unit")
            diff < 86400000 -> diff / 3600000 to Strings.get("hour_unit")
            diff < 259200000 -> diff / 86400000 to Strings.get("day_unit")
            else -> return toFormattedDate(milliseconds)
        }

        return if (isPaloEnglish()) "$value ${unit}${if (value > 1) "s" else ""} ago"
        else "${value.toBengaliNumber()} $unit আগে"
    }

    fun toFormattedDate(milliseconds: Long): String =
        SimpleDateFormat("dd-MM-yyyy hh:mm", locale)
            .format(Date(milliseconds))

}