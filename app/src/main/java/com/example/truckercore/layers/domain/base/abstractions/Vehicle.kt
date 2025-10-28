package com.example.truckercore.layers.domain.base.abstractions

import com.example.truckercore.layers.domain.base.contracts.entity.Entity
import com.example.truckercore.layers.domain.base.contracts.others.RigComposition
import com.example.truckercore.layers.domain.base.others.Chassi
import com.example.truckercore.layers.domain.base.others.Color
import com.example.truckercore.layers.domain.base.others.Plate
import com.example.truckercore.layers.domain.base.others.Renavam
import com.example.truckercore.layers.domain.base.others.YearModel
import com.example.truckercore.layers.domain.model.crlv.Crlv
import com.example.truckercore.layers.domain.model.crlv.CrlvCollection

abstract class Vehicle(
    open val color: Color,
    open val plate: Plate,
    open val chassi: Chassi? = null,
    open val renavam: Renavam? = null,
    open val yearModel: YearModel? = null
) : Entity {

    private val _crlvCollection = CrlvCollection()

    fun addAll(documents: List<Crlv>) = _crlvCollection.addAll(documents)

    fun getActiveCrlv(): Crlv? = _crlvCollection.getActive()

    fun hasCrlvExpiringSoon(withinDays: Long): Boolean = _crlvCollection.hasExpiringSoon(withinDays)

}