package com.example.truckercore.layers.domain.model.crlv

import com.example.truckercore.core.error.DomainException

class CrlvCollection(private val dataSet: MutableSet<Crlv> = mutableSetOf()) {

    val all: Set<Crlv> get() = dataSet.toSet()

    fun add(crlv: Crlv) {
        if (overlapsAnyExistent(crlv)) throw DomainException.RuleViolation(ERROR_MSG)
        dataSet.add(crlv)
    }

    fun getActiveCrlv(): Crlv? {
        return dataSet.firstOrNull { it.isActive }
    }

    private fun overlapsAnyExistent(newCrlv: Crlv): Boolean {
        return dataSet.any { it.overlaps(newCrlv.period) }
    }

    private companion object {
        private const val ERROR_MSG = "A CRLV with overlapping validity period already exists."
    }

}