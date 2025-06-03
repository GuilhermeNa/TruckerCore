package com.example.truckercore.model.modules._shared._contracts.entity

import com.example.truckercore._shared.expressions.getClassName
import java.util.UUID

interface ID {

    val value: String

    fun validate() {
        if (value.isBlank()) throw InvalidIdException(
            "Invalid ${this.getClassName()}: ID must be a non-blank string."
        )
    }

    companion object {
        fun generate(): String = UUID.randomUUID().toString()
    }

}