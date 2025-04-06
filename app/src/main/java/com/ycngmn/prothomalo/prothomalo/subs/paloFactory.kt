package com.ycngmn.prothomalo.prothomalo.subs

import com.ycngmn.prothomalo.prothomalo.ProthomAlo

object PaloFactory {
    private val instances = mutableMapOf<PaloKeys, ProthomAlo>()

    fun get(classKey: PaloKeys): ProthomAlo {
        return instances.getOrPut(classKey) {
            when (classKey) {
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
}