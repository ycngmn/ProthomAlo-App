package com.ycngmn.prothomalo.scraper.paloChilds

import com.ycngmn.prothomalo.scraper.ProthomAlo

object PaloFactory {
    fun get (classKey: String) : ProthomAlo {
        return when (classKey) {
            "PaloMain" -> ProthomAlo()
            "PaloEnglish" -> PaloEnglish()
            else -> throw IllegalArgumentException("Unknown class key: $classKey")
        }
    }
}