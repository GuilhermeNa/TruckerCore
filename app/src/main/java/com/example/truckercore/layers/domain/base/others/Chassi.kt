package com.example.truckercore.layers.domain.base.others

import com.example.truckercore.core.error.DomainException

@JvmInline
value class Chassi(val value: String) {

    init {
        validate()
    }

    private fun validate() {
        val match = Regex("^[A-HJ-NPR-Z0-9]{17}$").matches(value)
        if (!match) throw DomainException.RuleViolation(ERROR_MESSAGE)
    }

    private companion object {
        private const val ERROR_MESSAGE =
            "Invalid VIN (Chassis) format. It must contain exactly 17 characters (A–Z, 0–9) and cannot include I, O, or Q."
    }

}