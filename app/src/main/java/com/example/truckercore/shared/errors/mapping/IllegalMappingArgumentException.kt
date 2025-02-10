package com.example.truckercore.shared.errors.mapping

import com.example.truckercore.shared.errors.abstractions.MappingException

class IllegalMappingArgumentException(
    private val expected: Any,
    private val received: Any
) : MappingException() {

    fun getMessage(): String = "Expected a ${expected::class.simpleName} " +
            "for mapping but received: ${received::class.simpleName}."

}