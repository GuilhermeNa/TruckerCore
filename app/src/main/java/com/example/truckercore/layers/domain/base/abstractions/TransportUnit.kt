package com.example.truckercore.layers.domain.base.abstractions

import com.example.truckercore.layers.domain.base.contracts.entity.Entity
import com.example.truckercore.layers.domain.base.contracts.others.RigComposition
import com.example.truckercore.layers.domain.base.others.Chassi
import com.example.truckercore.layers.domain.base.others.Plate
import com.example.truckercore.layers.domain.base.others.Renavam
import com.example.truckercore.layers.domain.base.others.YearModel
import com.example.truckercore.layers.domain.model.crlv.Crlv
import com.example.truckercore.layers.domain.model.crlv.CrlvCollection
import com.example.truckercore.layers.domain.base.others.Color

abstract class TransportUnit(
    open val color: Color,
    open val plate: Plate,
    open val chassi: Chassi? = null,
    open val renavam: Renavam? = null,
    open val yearModel: YearModel? = null
): Entity, RigComposition {

    private val crlvCollection = CrlvCollection()

    fun addCrlv(crlv: Crlv) = crlvCollection.add(crlv)

    fun getCrlvs(): List<Crlv> = crlvCollection.toList()

    fun anyActiveCrlv(): Boolean = crlvCollection.anyActive()

    fun getActiveCrlv(): Crlv? =  crlvCollection.getActiveCrlv()

    fun hasCrlvExpiringSoon(): Boolean = crlvCollection.hasCrlvExpiringSoon()

}