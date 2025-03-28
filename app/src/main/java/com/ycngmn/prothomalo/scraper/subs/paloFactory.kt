package com.ycngmn.prothomalo.scraper.subs

import com.ycngmn.prothomalo.scraper.ProthomAlo

object PaloFactory {
    fun get (classKey: String) : ProthomAlo {
        return when (classKey) {
            "PaloMain" -> ProthomAlo()
            "PaloEnglish" -> PaloEnglish()
            "KishorAlo" -> KishoreAlo()
            "Mukti1971" -> Mukti1971()
            "BigganChinta" -> BigganChinta()
            "Nagorik" -> Nagorik()
            "BondhuShava" -> BondhuShava()
            "PaloTrust" -> PaloTrust()
            else -> throw IllegalArgumentException("Unknown class key: $classKey")
        }
    }
}