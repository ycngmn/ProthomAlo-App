package com.ycngmn.prothomalo.prothomalo

import com.ycngmn.prothomalo.prothomalo.subs.PaloFactory
import com.ycngmn.prothomalo.prothomalo.subs.PaloKeys

object PaloGlobal {
    var paloKey = PaloKeys.PaloMain
    var isDarkTheme = false
    fun getPalo() : ProthomAlo {
        return PaloFactory.get(paloKey)
    }
}