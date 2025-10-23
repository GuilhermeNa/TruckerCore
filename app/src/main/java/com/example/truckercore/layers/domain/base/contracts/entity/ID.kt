package com.example.truckercore.layers.domain.base.contracts.entity

import com.example.truckercore.core.error.DomainException
import com.example.truckercore.core.my_lib.expressions.getClassName
import java.util.UUID

interface ID {

    val value: String

    fun validate() {
        if (value.isBlank()) throw DomainException.RuleViolation(
            "Invalid ${this.getClassName()}: ID must be a non-blank string."
        )
    }

    companion object {
        fun generateRandomUUID(): String = UUID.randomUUID().toString()
    }

}