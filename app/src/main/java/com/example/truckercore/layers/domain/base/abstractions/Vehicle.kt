package com.example.truckercore.layers.domain.base.abstractions

import com.example.truckercore.core.error.DomainException
import com.example.truckercore.layers.domain.base.contracts.entity.Entity
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

    private val crlvs = CrlvCollection()

    fun initCrlvsFromDatabase(dbCrlvs: List<Crlv>) {
        dbCrlvs.forEach { registerCrlv(it) }
    }

    fun registerCrlv(crlv: Crlv) {
        if (crlvs.overlapsAny(crlv)) {
            throw DomainException.RuleViolation(CRLV_OVERLAPS_ERROR)
        } else crlvs.add(crlv)
    }

    fun getActiveCrlv(): Crlv? = crlvs.getActive()

    fun hasCrlvExpiringSoon(withinDays: Long): Boolean = crlvs.hasExpiringSoon(withinDays)

    companion object {
        private const val CRLV_OVERLAPS_ERROR =
            "Cannot register CRLV: the provided document period overlaps with an existing CRLV."
    }

}