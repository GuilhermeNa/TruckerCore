package com.example.truckercore.layers.domain.base.others

import com.example.truckercore.core.error.DomainException

@JvmInline
value class Plate(val value: String) {

    init {
        validate()
    }

    private fun validate() {
        val match = Regex("^[A-Z]{3}[0-9][A-Z][0-9]{2}$").matches(value)
        if (!match) throw DomainException.RuleViolation(ERROR_MESSAGE)
    }

    private companion object {
        private const val ERROR_MESSAGE = "Invalid Brazilian license plate format (e.g., ABC1D23)."
    }

}