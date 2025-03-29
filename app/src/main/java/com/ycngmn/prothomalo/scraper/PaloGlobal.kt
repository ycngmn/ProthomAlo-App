package com.ycngmn.prothomalo.scraper

import com.ycngmn.prothomalo.scraper.subs.PaloFactory
import com.ycngmn.prothomalo.scraper.subs.PaloKeys

object PaloGlobal {
    var paloKey = PaloKeys.PaloMain
    var isDarkTheme = false
    fun getPalo() : ProthomAlo {
        return PaloFactory.get(paloKey)
    }
}