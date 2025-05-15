package com.example.truckercore.model.modules._contracts

import com.example.truckercore.model.errors.domain.DomainException
import java.util.UUID

interface ID {

    val value: String

    fun validate() {
        if (value.isBlank()) throw DomainException.InvalidForCreation(
            "Invalid ${this.javaClass.simpleName}: ID must be a non-blank string."
        )
    }

    companion object {
        fun generate(): String = UUID.randomUUID().toString()
    }

}