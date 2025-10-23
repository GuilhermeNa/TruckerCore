package com.example.truckercore.layers.domain.base.others

import com.example.truckercore.core.error.DomainException

@JvmInline
value class Renavam(val value: String){

    init {
        validate()
    }

    private fun validate() {
        val match = Regex("^\\d{11}$").matches(value)
        if (!match) throw DomainException.RuleViolation(ERROR_MESSAGE)
    }

    private companion object {
        private const val ERROR_MESSAGE = "Invalid RENAVAM format. It must contain exactly 11 numeric digits (e.g., 00512345678)."
    }

}