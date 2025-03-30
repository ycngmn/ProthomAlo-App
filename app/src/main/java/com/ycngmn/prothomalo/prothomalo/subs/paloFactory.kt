package com.ycngmn.prothomalo.prothomalo.subs

import com.ycngmn.prothomalo.prothomalo.ProthomAlo

enum class PaloKeys {
    PaloMain, PaloEnglish,
    KishorAlo, Mukti1971,
    BigganChinta, Nagorik,
    BondhuShava, PaloTrust
}

object PaloFactory {
    fun get (classKey: PaloKeys) : ProthomAlo {
        return when (classKey) {
            PaloKeys.PaloMain -> ProthomAlo()
            PaloKeys.PaloEnglish -> PaloEnglish()
            PaloKeys.KishorAlo -> KishoreAlo()
            PaloKeys.Mukti1971 -> Mukti1971()
            PaloKeys.BigganChinta -> BigganChinta()
            PaloKeys.Nagorik -> Nagorik()
            PaloKeys.BondhuShava -> BondhuShava()
            PaloKeys.PaloTrust -> PaloTrust()
        }
    }
}