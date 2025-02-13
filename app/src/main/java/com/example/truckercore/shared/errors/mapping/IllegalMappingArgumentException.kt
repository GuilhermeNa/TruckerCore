package com.example.truckercore.shared.errors.mapping

import com.example.truckercore.shared.errors.abstractions.MappingException

class IllegalMappingArgumentException(
    val expected: Any,
    val received: Any
) : MappingException() {

    fun message(): String = "Expected a ${expected::class.simpleName} " +
            "for mapping but received: ${received::class.simpleName}."

}