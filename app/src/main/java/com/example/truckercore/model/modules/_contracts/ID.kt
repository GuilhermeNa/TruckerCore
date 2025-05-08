package com.example.truckercore.model.modules._contracts

import java.util.UUID

interface ID {

    val value: String

    fun validate() {
        if (value.isBlank()) throw InvalidIdException(
            "Invalid ${this.javaClass.simpleName}: ID must be a non-blank string."
        )
    }

    companion object {
        fun generate(): String = UUID.randomUUID().toString()
    }

}

class InvalidIdException(message: String? = null, cause: Throwable? = null) :
    Exception(message, cause)