package com.example.truckercore.layers.domain.base.contracts.others

import com.example.truckercore.layers.domain.model.crlv.Crlv

interface CrlvOwner {

    fun addCrlv(crlv: Crlv)

    fun getCrlvs(): Set<Crlv>

    fun hasActiveCrlv(): Boolean

    fun getActiveCrlv(): Crlv?

}